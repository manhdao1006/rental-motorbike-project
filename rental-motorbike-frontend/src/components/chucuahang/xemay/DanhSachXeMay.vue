<template>
    <div class="container-fluid">
        <div class="col-12 col-lg-12 col-xxl-12 d-flex">
            <div class="card flex-fill">
                <div class="row">
                    <div class="col-6">
                        <SearchComponent class="w-100" v-model="keyword" />
                    </div>
                    <div class="col-6 text-end pt-2 pe-4">
                        <div class="dropdown">
                            <a
                                class="btn btn-outline-dark dropdown-toggle"
                                href="#"
                                role="button"
                                id="dropdownMenuLink"
                                data-bs-toggle="dropdown"
                                aria-expanded="false"
                            >
                                {{ selectedSortLabel }}
                            </a>
                            <ul class="dropdown-menu" aria-labelledby="dropdownMenuLink">
                                <li>
                                    <button class="dropdown-item" @click="setSortOption('moiNhat')">
                                        Mới nhất
                                    </button>
                                </li>
                                <li>
                                    <button class="dropdown-item" @click="setSortOption('cuNhat')">
                                        Cũ nhất
                                    </button>
                                </li>
                            </ul>
                        </div>
                    </div>
                </div>

                <div class="row justify-content-evenly">
                    <div class="card-header col-xl-6">
                        <h5 class="card-title mb-0">Danh sách xe máy</h5>
                    </div>
                    <div class="card-header col-xl-6 text-end">
                        <router-link class="text-success" :to="{ name: 'ThemMoiXeMayView' }">
                            <i class="fas fa-plus-circle"></i>
                            <span class="ps-1">Thêm mới</span>
                        </router-link>
                    </div>
                </div>
                <table class="table table-hover my-0">
                    <thead>
                        <tr>
                            <th>STT</th>
                            <th>Mã xe</th>
                            <th>Tên xe</th>
                            <th>Biển số xe</th>
                            <th>Danh mục xe</th>
                            <th>Giá thuê</th>
                            <th>Trạng thái</th>
                            <th>Thao tác</th>
                        </tr>
                    </thead>
                    <tbody>
                        <tr v-if="paginatedXeMays.length === 0">
                            <td colspan="6" class="text-center text-muted fst-italic">
                                Không có xe máy nào
                            </td>
                        </tr>
                        <tr
                            v-for="(item, index) in paginatedXeMays"
                            :key="(item as any).xeMay.maXeMay"
                        >
                            <td>
                                {{ index + 1 }}
                            </td>
                            <td>
                                {{ (item as any).xeMay.maXeMay }}
                            </td>
                            <td>
                                {{ (item as any).xeMay.tenXe }}
                            </td>
                            <td>
                                {{ (item as any).xeMay.bienSoXe }}
                            </td>
                            <td>
                                {{ (item as any).danhMucXe.tenDanhMucXe }}
                            </td>
                            <td>{{ formattedGiaThue((item as any).xeMay.giaThue) }} VNĐ</td>
                            <td>
                                {{ (item as any).xeMay.trangThaiHoatDong }}
                            </td>
                            <td>
                                <router-link
                                    :to="{
                                        name: 'CapNhatXeMayView',
                                        params: { maXeMay: String((item as any).xeMay.maXeMay) }
                                    }"
                                >
                                    <i class="far fa-edit text-success" title="Cập nhật"></i
                                ></router-link>
                                |
                                <button
                                    @click="showConfirmPopup(String((item as any).xeMay.maXeMay))"
                                    class="border-0 p-0 bg-transparent"
                                >
                                    <i class="fas fa-trash-alt text-danger" title="Xóa"></i>
                                </button>
                            </td>
                        </tr>
                    </tbody>
                </table>
            </div>
        </div>
        <PaginationComponent
            v-if="paginatedXeMays.length > 0"
            class="text-center mt-3"
            :currentPage="currentPage"
            :totalPages="totalPages"
            :totalElements="totalElements"
            :pageSize="pageSize"
            @pageChanged="onChangePage"
        />

        <PopupDelete
            :showPopup="showDeletePopup"
            @update:showPopup="showDeletePopup = $event"
            :onDelete="confirmDelete"
        />
    </div>
    <PopupLoading :isLoading="isLoadingPage" />
</template>

<script lang="ts">
    import PaginationComponent from '@/components/dungchung/PaginationComponent.vue'
    import PopupDelete from '@/components/dungchung/PopupDelete.vue'
    import PopupLoading from '@/components/dungchung/PopupLoading.vue'
    import SearchComponent from '@/components/dungchung/SearchComponent.vue'
    import { getMaNguoiDung } from '@/services/localStorageService'
    import { deleteXeMay, getXeMaysByChuCuaHang } from '@/services/xeMayService'
    import { computed, defineComponent, onMounted, ref, Ref, watch } from 'vue'
    import { useRoute, useRouter } from 'vue-router'
    export default defineComponent({
        name: 'DanhSachXeMay',
        components: {
            SearchComponent,
            PaginationComponent,
            PopupDelete,
            PopupLoading
        },
        setup() {
            const totalPages = computed(() => Math.ceil(xeMays.value.length / pageSize.value))
            const route = useRoute()
            const router = useRouter()
            const currentPage = ref(Number(route.query.page) || 1) as Ref<number>
            const xeMays: Ref<Record<string, unknown>[]> = ref([])
            const tenChuCuaHangs = ref<Record<string, string>>({})
            const totalElements = ref() as Ref<number>
            const pageSize = ref(10) as Ref<number>
            const showDeletePopup = ref(false) as Ref<boolean>
            const xeMayToDelete = ref(null) as Ref<string | null>
            const keyword = ref('') as Ref<string>
            const isLoadingPage = ref(true)
            const selectedSort = ref<'moiNhat' | 'cuNhat'>('moiNhat')

            const selectedSortLabel = computed(() => {
                return selectedSort.value === 'moiNhat' ? 'Mới nhất' : 'Cũ nhất'
            })

            const setSortOption = (option: 'moiNhat' | 'cuNhat') => {
                selectedSort.value = option
                fetchXeMays()
            }

            const filteredXeMays = computed(() => {
                if (!keyword.value.trim()) return xeMays.value

                // eslint-disable-next-line @typescript-eslint/no-explicit-any
                return xeMays.value.filter((item: any) => {
                    const maXeMay = String(item.xeMay.maXeMay).toLowerCase()
                    const tenXe = String(item.xeMay.tenXe).toLowerCase()
                    const bienSoXe = String(item.xeMay.bienSoXe).toLowerCase()
                    const tenDanhMucXe = String(item.danhMucXe.tenDanhMucXe).toLowerCase()
                    const giaThue = String(item.xeMay.giaThue).toLowerCase()
                    const trangThaiHoatDong = String(item.xeMay.trangThaiHoatDong).toLowerCase()

                    const kw = keyword.value.trim().toLowerCase()

                    return (
                        maXeMay.includes(kw) ||
                        tenXe.includes(kw) ||
                        bienSoXe.includes(kw) ||
                        tenDanhMucXe.includes(kw) ||
                        giaThue.includes(kw) ||
                        trangThaiHoatDong.includes(kw)
                    )
                })
            })

            const formattedGiaThue = (giaThue: string) => {
                if (!giaThue) return '0'
                const numberGiaThue = Number(giaThue)
                return numberGiaThue.toLocaleString('vi-VN')
            }

            const fetchXeMays = async () => {
                const result = await getXeMaysByChuCuaHang(getMaNguoiDung())

                result.sort(
                    (a: { xeMay: { maXeMay: string } }, b: { xeMay: { maXeMay: string } }) => {
                        const getNgayVaThuTu = (ma: string): { date: number; order: number } => {
                            const raw = ma.replace('XM', '')
                            const datePart = raw.slice(0, 8)
                            const orderPart = raw.slice(8) || '0'

                            const year = datePart.slice(0, 4)
                            const month = datePart.slice(4, 6)
                            const day = datePart.slice(6, 8)
                            const isoDate = `${year}-${month}-${day}`

                            return {
                                date: new Date(isoDate).getTime(),
                                order: parseInt(orderPart, 10)
                            }
                        }

                        const infoA = getNgayVaThuTu(a.xeMay.maXeMay)
                        const infoB = getNgayVaThuTu(b.xeMay.maXeMay)

                        if (infoA.date !== infoB.date) {
                            return selectedSort.value === 'moiNhat'
                                ? infoB.date - infoA.date
                                : infoA.date - infoB.date
                        }

                        return selectedSort.value === 'moiNhat'
                            ? infoB.order - infoA.order
                            : infoA.order - infoB.order
                    }
                )

                xeMays.value = result.filter(
                    (item: { xeMay: { maXeMay: string } }, index: number, self: []) =>
                        index ===
                        self.findIndex(
                            (t: { xeMay: { maXeMay: string } }) =>
                                t.xeMay.maXeMay === item.xeMay.maXeMay
                        )
                )
            }

            const paginatedXeMays = computed(() => {
                const start = (currentPage.value - 1) * pageSize.value
                return filteredXeMays.value.slice(start, start + pageSize.value)
            })

            watch(currentPage, (newPage) => {
                router.replace({ query: { ...route.query, page: newPage.toString() } })
                fetchXeMays()
            })

            const onChangePage = (page: number) => {
                currentPage.value = page
            }

            const showConfirmPopup = (maDanhMucXe: unknown) => {
                if (typeof maDanhMucXe === 'string') {
                    xeMayToDelete.value = maDanhMucXe
                    showDeletePopup.value = true
                } else {
                    console.error('Lỗi: ', maDanhMucXe)
                }
            }

            const confirmDelete = async () => {
                if (xeMayToDelete.value) {
                    await deleteXeMay(xeMayToDelete.value)
                    fetchXeMays()
                }
            }

            onMounted(async () => {
                await Promise.all([fetchXeMays()])
                isLoadingPage.value = false
            })

            return {
                isLoadingPage,
                paginatedXeMays,
                currentPage,
                totalPages,
                totalElements,
                pageSize,
                onChangePage,
                showConfirmPopup,
                confirmDelete,
                showDeletePopup,
                xeMayToDelete,
                tenChuCuaHangs,
                keyword,
                formattedGiaThue,
                selectedSortLabel,
                setSortOption
            }
        }
    })
</script>

<style>
    td a {
        text-decoration: none;
        color: #000000;

        &:hover {
            color: #000000;
        }
    }
</style>
