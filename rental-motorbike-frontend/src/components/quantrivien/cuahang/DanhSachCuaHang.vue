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
                        <h5 class="card-title mb-0">Danh sách cửa hàng</h5>
                    </div>
                    <div class="card-header col-xl-6 text-end"></div>
                </div>
                <table class="table table-hover my-0">
                    <thead>
                        <tr>
                            <th>STT</th>
                            <th>Tên cửa hàng</th>
                            <th>SĐT</th>
                            <th>Địa chỉ</th>
                            <th>Chủ cửa hàng</th>
                            <th>Trạng thái</th>
                            <th>Thao tác</th>
                        </tr>
                    </thead>
                    <tbody>
                        <tr v-if="paginatedChuCuaHangs.length === 0">
                            <td colspan="6" class="text-center text-muted fst-italic">
                                Không có cửa hàng nào
                            </td>
                        </tr>
                        <tr
                            v-for="(item, index) in paginatedChuCuaHangs"
                            :key="
                                typeof (item as any).chuCuaHang.maChuCuaHang === 'string'
                                    ? (item as any).chuCuaHang.maChuCuaHang
                                    : undefined
                            "
                        >
                            <td>
                                {{ index + 1 }}
                            </td>
                            <td>
                                {{ (item as any).chuCuaHang.tenCuaHang }}
                            </td>
                            <td>
                                {{ (item as any).chuCuaHang.soDienThoaiCuaHang }}
                            </td>
                            <td>
                                {{ (item as any).chuCuaHang.diaChiCuaHang }}
                            </td>
                            <td>
                                {{ (item as any).nguoiDung.hoVaTen }}
                            </td>
                            <td>
                                {{ (item as any).nguoiDung.trangThaiHoatDong }}
                            </td>
                            <td>
                                <button
                                    @click="
                                        showConfirmPopup(
                                            String((item as any).chuCuaHang.maChuCuaHang)
                                        )
                                    "
                                    class="border-0 p-0 bg-transparent"
                                >
                                    <i class="fas fa-trash-alt text-danger" title="Xóa"></i>
                                </button>
                                <router-link
                                    :to="{
                                        name: 'ChiTietCuaHangView',
                                        params: { maChuCuaHang: String((item as any).chuCuaHang.maChuCuaHang) }
                                    }"
                                    v-if="(item as any).nguoiDung.trangThaiHoatDong === 'Chờ duyệt'"
                                >
                                    | <i class="fas fa-recycle text-warning" title="Xử lý"></i>
                                </router-link>
                            </td>
                        </tr>
                    </tbody>
                </table>
            </div>
        </div>
        <PaginationComponent
            v-if="paginatedChuCuaHangs.length > 0"
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
    import { deleteChuCuaHang, getChuCuaHangs } from '@/services/chuCuaHangService'
    import { computed, defineComponent, onMounted, ref, Ref, watch } from 'vue'
    import { useRoute, useRouter } from 'vue-router'

    export default defineComponent({
        name: 'DanhSachChuCuaHang',
        components: {
            SearchComponent,
            PaginationComponent,
            PopupDelete,
            PopupLoading
        },
        setup() {
            const totalPages = computed(() => Math.ceil(chuCuaHangs.value.length / pageSize.value))
            const route = useRoute()
            const router = useRouter()
            const currentPage = ref(Number(route.query.page) || 1) as Ref<number>
            const chuCuaHangs: Ref<Record<string, unknown>[]> = ref([])
            const totalElements = ref() as Ref<number>
            const pageSize = ref(10) as Ref<number>
            const showDeletePopup = ref(false) as Ref<boolean>
            const chuCuaHangToDelete = ref(null) as Ref<string | null>
            const keyword = ref('') as Ref<string>
            const isLoadingPage = ref(true)
            const selectedSort = ref<'moiNhat' | 'cuNhat'>('moiNhat')

            const selectedSortLabel = computed(() => {
                return selectedSort.value === 'moiNhat' ? 'Mới nhất' : 'Cũ nhất'
            })

            const setSortOption = (option: 'moiNhat' | 'cuNhat') => {
                selectedSort.value = option
                fetchChuCuaHangs()
            }

            const filteredChuCuaHangs = computed(() => {
                if (!keyword.value.trim()) return chuCuaHangs.value

                // eslint-disable-next-line @typescript-eslint/no-explicit-any
                return chuCuaHangs.value.filter((item: any) => {
                    const maChuCuaHang = String(item.chuCuaHang.maChuCuaHang).toLowerCase()
                    const tenCuaHang = String(item.chuCuaHang.tenCuaHang).toLowerCase()
                    const soDienThoaiCuaHang = String(
                        item.chuCuaHang.soDienThoaiCuaHang
                    ).toLowerCase()
                    const diaChiCuaHang = String(item.chuCuaHang.diaChiCuaHang).toLowerCase()
                    const trangThaiHoatDong = String(item.nguoiDung.trangThaiHoatDong).toLowerCase()
                    const hoVaTen = String(item.nguoiDung.hoVaTen).toLowerCase()

                    const kw = keyword.value.trim().toLowerCase()

                    return (
                        maChuCuaHang.includes(kw) ||
                        hoVaTen.includes(kw) ||
                        tenCuaHang.includes(kw) ||
                        soDienThoaiCuaHang.includes(kw) ||
                        diaChiCuaHang.includes(kw) ||
                        trangThaiHoatDong.includes(kw)
                    )
                })
            })

            const fetchChuCuaHangs = async () => {
                const result = await getChuCuaHangs()
                result.sort(
                    (
                        firstDate: { nguoiDung: { ngayDangKy: string | number | Date } },
                        secondDate: { nguoiDung: { ngayDangKy: string | number | Date } }
                    ) => {
                        const dateFirst = new Date(firstDate.nguoiDung.ngayDangKy).getTime()
                        const dateSecond = new Date(secondDate.nguoiDung.ngayDangKy).getTime()
                        return selectedSort.value === 'moiNhat'
                            ? dateSecond - dateFirst
                            : dateFirst - dateSecond
                    }
                )

                chuCuaHangs.value = result.filter(
                    (item: { chuCuaHang: { maChuCuaHang: string } }, index: number, self: []) =>
                        index ===
                        self.findIndex(
                            (t: { chuCuaHang: { maChuCuaHang: string } }) =>
                                t.chuCuaHang.maChuCuaHang === item.chuCuaHang.maChuCuaHang
                        )
                )
            }

            const paginatedChuCuaHangs = computed(() => {
                const start = (currentPage.value - 1) * pageSize.value
                return filteredChuCuaHangs.value.slice(start, start + pageSize.value)
            })

            watch(currentPage, (newPage) => {
                router.replace({ query: { ...route.query, page: newPage.toString() } })
                fetchChuCuaHangs()
            })

            const onChangePage = (page: number) => {
                currentPage.value = page
            }

            const showConfirmPopup = (maChuCuaHang: unknown) => {
                if (typeof maChuCuaHang === 'string') {
                    chuCuaHangToDelete.value = maChuCuaHang
                    showDeletePopup.value = true
                } else {
                    console.error('Lỗi: ', maChuCuaHang)
                }
            }

            const confirmDelete = async () => {
                if (chuCuaHangToDelete.value) {
                    await deleteChuCuaHang(chuCuaHangToDelete.value)
                    fetchChuCuaHangs()
                }
            }

            onMounted(async () => {
                await Promise.all([fetchChuCuaHangs()])
                isLoadingPage.value = false
            })

            return {
                isLoadingPage,
                paginatedChuCuaHangs,
                currentPage,
                totalPages,
                totalElements,
                pageSize,
                onChangePage,
                showDeletePopup,
                chuCuaHangToDelete,
                showConfirmPopup,
                confirmDelete,
                keyword,
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
