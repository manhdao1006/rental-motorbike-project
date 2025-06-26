<template>
    <div class="container-fluid">
        <div class="col-12 col-lg-12 col-xxl-12 d-flex">
            <div class="card flex-fill">
                <div class="row justify-content-center">
                    <div class="btn-group">
                        <router-link
                            :to="{
                                name: 'DanhSachNguoiDungView',
                                params: { maVaiTro: 'VT2' }
                            }"
                            class="btn btn-primary text-uppercase border-start border-end"
                            :class="{ active: maVaiTroParams === 'VT2' }"
                            >Chủ cửa hàng</router-link
                        >
                        <router-link
                            :to="{
                                name: 'DanhSachNguoiDungView',
                                params: { maVaiTro: 'VT3' }
                            }"
                            class="btn btn-primary text-uppercase border-start"
                            :class="{ active: maVaiTroParams === 'VT3' }"
                            >Nhân viên</router-link
                        >
                        <router-link
                            :to="{
                                name: 'DanhSachNguoiDungView',
                                params: { maVaiTro: 'VT4' }
                            }"
                            class="btn btn-primary text-uppercase border-start"
                            :class="{ active: maVaiTroParams === 'VT4' }"
                            >Khách hàng</router-link
                        >
                    </div>
                </div>
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
                        <h5 class="card-title mb-0">Danh sách người dùng</h5>
                    </div>
                    <div class="card-header col-xl-6 text-end">
                        <router-link
                            v-if="maVaiTroParams === 'VT2'"
                            class="text-success"
                            :to="{
                                name: 'ThemMoiChuCuaHangView',
                                params: { maVaiTro: maVaiTroParams }
                            }"
                        >
                            <i class="fas fa-plus-circle"></i>
                            <span class="ps-1">Thêm mới</span>
                        </router-link>
                        <!-- <router-link
                            v-if="maVaiTroParams === 'VT3'"
                            class="text-success"
                            :to="{
                                name: 'ThemMoiNhanVienView',
                                params: { maVaiTro: maVaiTroParams }
                            }"
                        >
                            <i class="fas fa-plus-circle"></i>
                            <span class="ps-1">Thêm mới</span>
                        </router-link> -->
                        <router-link
                            v-if="maVaiTroParams === 'VT4'"
                            class="text-success"
                            :to="{
                                name: 'ThemMoiKhachHangView',
                                params: { maVaiTro: maVaiTroParams }
                            }"
                        >
                            <i class="fas fa-plus-circle"></i>
                            <span class="ps-1">Thêm mới</span>
                        </router-link>
                    </div>
                </div>
                <table class="table table-hover my-0">
                    <thead>
                        <tr>
                            <th>STT</th>
                            <th>Mã người dùng</th>
                            <th>Họ và tên</th>
                            <th>Email</th>
                            <th>Số điện thoại</th>
                            <th>Trạng thái hoạt động</th>
                            <th>Thao tác</th>
                        </tr>
                    </thead>
                    <tbody>
                        <tr v-if="paginatedNguoiDungs.length === 0">
                            <td colspan="6" class="text-center text-muted fst-italic">
                                Không có người dùng nào
                            </td>
                        </tr>
                        <tr
                            v-for="(nguoiDung, index) in paginatedNguoiDungs"
                            :key="
                                typeof nguoiDung.maNguoiDung === 'string'
                                    ? nguoiDung.maNguoiDung
                                    : undefined
                            "
                        >
                            <td>
                                {{ index + 1 }}
                            </td>
                            <td>
                                {{ nguoiDung.maNguoiDung }}
                            </td>
                            <td>
                                {{ nguoiDung.hoVaTen }}
                            </td>
                            <td>
                                {{ nguoiDung.email }}
                            </td>
                            <td>
                                {{ nguoiDung.soDienThoai }}
                            </td>
                            <td>
                                {{ nguoiDung.trangThaiHoatDong }}
                            </td>
                            <td>
                                <router-link
                                    v-if="maVaiTroParams === 'VT2'"
                                    :to="{
                                        name: 'CapNhatChuCuaHangView',
                                        params: {
                                            maVaiTro: maVaiTroParams,
                                            maNguoiDung: String(nguoiDung.maNguoiDung)
                                        }
                                    }"
                                >
                                    <i class="far fa-edit text-success" title="Cập nhật"></i
                                ></router-link>
                                <router-link
                                    v-if="maVaiTroParams === 'VT3'"
                                    :to="{
                                        name: 'CapNhatNhanVienView',
                                        params: {
                                            maVaiTro: maVaiTroParams,
                                            maNguoiDung: String(nguoiDung.maNguoiDung)
                                        }
                                    }"
                                >
                                    <i class="far fa-edit text-success" title="Cập nhật"></i
                                ></router-link>
                                <router-link
                                    v-if="maVaiTroParams === 'VT4'"
                                    :to="{
                                        name: 'CapNhatKhachHangView',
                                        params: {
                                            maVaiTro: maVaiTroParams,
                                            maNguoiDung: String(nguoiDung.maNguoiDung)
                                        }
                                    }"
                                >
                                    <i class="far fa-edit text-success" title="Cập nhật"></i
                                ></router-link>
                                |
                                <button
                                    @click="showConfirmDeletePopup(String(nguoiDung.maNguoiDung))"
                                    class="border-0 p-0 bg-transparent"
                                >
                                    <i class="fas fa-trash-alt text-danger" title="Xóa"></i>
                                </button>
                                |
                                <button
                                    @click="showConfirmBanPopup(String(nguoiDung.maNguoiDung))"
                                    class="border-0 p-0 bg-transparent"
                                >
                                    <i class="fa-solid fa-ban text-warning" title="Cấm"></i>
                                </button>
                            </td>
                        </tr>
                    </tbody>
                </table>
            </div>
        </div>
        <PaginationComponent
            v-if="paginatedNguoiDungs.length > 0"
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
        <PopupBan
            :showPopup="showBanPopup"
            @update:showPopup="showBanPopup = $event"
            :onBan="confirmBan"
        />
    </div>
    <PopupLoading :isLoading="isLoadingPage" />
</template>

<script lang="ts">
    import PaginationComponent from '@/components/dungchung/PaginationComponent.vue'
    import PopupBan from '@/components/dungchung/PopupBan.vue'
    import PopupDelete from '@/components/dungchung/PopupDelete.vue'
    import PopupLoading from '@/components/dungchung/PopupLoading.vue'
    import SearchComponent from '@/components/dungchung/SearchComponent.vue'
    import { banChuCuaHang, deleteChuCuaHang } from '@/services/chuCuaHangService'
    import { banKhachHang, deleteKhachHang } from '@/services/khachHangService'
    import { getNguoiDungsByMaVaiTro } from '@/services/nguoiDungService'
    import { banNhanVien, deleteNhanVien } from '@/services/nhanVienService'
    import { computed, defineComponent, onMounted, ref, Ref, watch } from 'vue'
    import { useRoute, useRouter } from 'vue-router'

    export default defineComponent({
        name: 'DanhSachNguoiDung',
        components: {
            SearchComponent,
            PaginationComponent,
            PopupDelete,
            PopupBan,
            PopupLoading
        },
        setup() {
            const totalPages = computed(() => Math.ceil(nguoiDungs.value.length / pageSize.value))
            const route = useRoute()
            const router = useRouter()
            const currentPage = ref(Number(route.query.page) || 1) as Ref<number>
            const nguoiDungs: Ref<Record<string, unknown>[]> = ref([])
            const totalElements = ref() as Ref<number>
            const pageSize = ref(10) as Ref<number>
            const showDeletePopup = ref(false) as Ref<boolean>
            const showBanPopup = ref(false) as Ref<boolean>
            const nguoiDungToDelete = ref(null) as Ref<string | null>
            const nguoiDungToBan = ref(null) as Ref<string | null>
            const keyword = ref('') as Ref<string>
            const maVaiTroParams = String(route.params.maVaiTro)
            const isLoadingPage = ref(true)
            const selectedSort = ref<'moiNhat' | 'cuNhat'>('moiNhat')

            const selectedSortLabel = computed(() => {
                return selectedSort.value === 'moiNhat' ? 'Mới nhất' : 'Cũ nhất'
            })

            const setSortOption = (option: 'moiNhat' | 'cuNhat') => {
                selectedSort.value = option
                fetchNguoiDungs()
            }

            const filteredNguoiDungs = computed(() => {
                if (!keyword.value.trim()) return nguoiDungs.value

                // eslint-disable-next-line @typescript-eslint/no-explicit-any
                return nguoiDungs.value.filter((item: any) => {
                    const maNguoiDung = String(item.maNguoiDung).toLowerCase()
                    const hoVaTen = String(item.hoVaTen).toLowerCase()
                    const email = String(item.email).toLowerCase()
                    const soDienThoai = String(item.soDienThoai).toLowerCase()
                    const trangThaiHoatDong = String(item.trangThaiHoatDong).toLowerCase()

                    const kw = keyword.value.trim().toLowerCase()

                    return (
                        maNguoiDung.includes(kw) ||
                        hoVaTen.includes(kw) ||
                        email.includes(kw) ||
                        soDienThoai.includes(kw) ||
                        trangThaiHoatDong.includes(kw)
                    )
                })
            })

            const fetchNguoiDungs = async () => {
                const result = await getNguoiDungsByMaVaiTro(String(route.params.maVaiTro))
                nguoiDungs.value = result
                    .filter(
                        (nguoiDung: { trangThaiHoatDong: string }) =>
                            nguoiDung.trangThaiHoatDong !== 'Từ chối'
                    )
                    .sort((a: { maNguoiDung: string }, b: { maNguoiDung: string }) => {
                        const getNgayVaThuTu = (ma: string): { date: number; order: number } => {
                            const raw = ma.replace('ND', '')
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

                        const infoA = getNgayVaThuTu(a.maNguoiDung)
                        const infoB = getNgayVaThuTu(b.maNguoiDung)

                        if (infoA.date !== infoB.date) {
                            return selectedSort.value === 'moiNhat'
                                ? infoB.date - infoA.date
                                : infoA.date - infoB.date
                        }

                        return selectedSort.value === 'moiNhat'
                            ? infoB.order - infoA.order
                            : infoA.order - infoB.order
                    })
            }

            const paginatedNguoiDungs = computed(() => {
                const start = (currentPage.value - 1) * pageSize.value
                return filteredNguoiDungs.value.slice(start, start + pageSize.value)
            })

            onMounted(async () => {
                await Promise.all([fetchNguoiDungs()])
                isLoadingPage.value = false
            })

            watch(currentPage, (newPage) => {
                router.replace({ query: { ...route.query, page: newPage.toString() } })
                fetchNguoiDungs()
            })

            watch(
                () => route.params.maVaiTro,
                () => {
                    window.location.reload()
                }
            )

            const onChangePage = (page: number) => {
                currentPage.value = page
            }

            const showConfirmDeletePopup = (maNguoiDung: unknown) => {
                if (typeof maNguoiDung === 'string') {
                    nguoiDungToDelete.value = maNguoiDung
                    showDeletePopup.value = true
                } else {
                    console.error('Lỗi: ', maNguoiDung)
                }
            }

            const showConfirmBanPopup = (maNguoiDung: unknown) => {
                if (typeof maNguoiDung === 'string') {
                    nguoiDungToBan.value = maNguoiDung
                    showBanPopup.value = true
                } else {
                    console.error('Lỗi: ', maNguoiDung)
                }
            }

            const confirmDelete = async () => {
                if (!nguoiDungToDelete.value) return

                let response
                if (maVaiTroParams === 'VT2') {
                    response = await deleteChuCuaHang(nguoiDungToDelete.value)
                } else if (maVaiTroParams === 'VT3') {
                    response = await deleteNhanVien(nguoiDungToDelete.value)
                } else if (maVaiTroParams === 'VT4') {
                    response = await deleteKhachHang(nguoiDungToDelete.value)
                }

                if (response?.success) {
                    router.go(0)
                } else {
                    alert(response?.message || 'Có lỗi xảy ra khi xóa')
                }
            }

            const confirmBan = async () => {
                if (!nguoiDungToBan.value) return

                let response
                if (maVaiTroParams === 'VT2') {
                    response = await banChuCuaHang(nguoiDungToBan.value)
                } else if (maVaiTroParams === 'VT3') {
                    response = await banNhanVien(nguoiDungToBan.value)
                } else if (maVaiTroParams === 'VT4') {
                    response = await banKhachHang(nguoiDungToBan.value)
                }

                if (response?.success) {
                    router.go(0)
                } else {
                    alert(response?.message || 'Có lỗi xảy ra khi ban')
                }
            }

            return {
                isLoadingPage,
                paginatedNguoiDungs,
                currentPage,
                totalPages,
                totalElements,
                pageSize,
                onChangePage,
                showDeletePopup,
                showBanPopup,
                keyword,
                showConfirmDeletePopup,
                showConfirmBanPopup,
                confirmDelete,
                confirmBan,
                maVaiTroParams,
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
    .btn-primary {
        --bs-btn-color: #fff;
        --bs-btn-bg: #ff8435;
        --bs-btn-border-color: #ff8435;
        --bs-btn-hover-color: #fff;
        --bs-btn-hover-bg: #81561d;
        --bs-btn-hover-border-color: #81561d;
        --bs-btn-focus-shadow-rgb: 49, 132, 253;
        --bs-btn-active-color: #fff;
        --bs-btn-active-bg: #81561d;
        --bs-btn-active-border-color: #81561d;
        --bs-btn-active-shadow: inset 0 3px 5px rgba(0, 0, 0, 0.125);
        --bs-btn-disabled-color: #fff;
        --bs-btn-disabled-bg: #ff8435;
        --bs-btn-disabled-border-color: #ff8435;
    }
</style>
