<template>
    <div class="container-fluid">
        <div class="col-12 col-lg-12 col-xxl-12 d-flex">
            <div class="card flex-fill">
                <div class="row justify-content-center">
                    <div class="btn-group">
                        <router-link
                            :to="{
                                name: 'DanhSachDonHangView',
                                params: { trangThaiDonHang: 'Chờ xử lý' }
                            }"
                            class="btn btn-primary text-uppercase border-start border-end"
                            :class="{ active: trangThaiDonHangParams === 'Chờ xử lý' }"
                            >Chờ xử lý</router-link
                        >
                        <router-link
                            :to="{
                                name: 'DanhSachDonHangView',
                                params: { trangThaiDonHang: 'Đang giao xe' }
                            }"
                            class="btn btn-primary text-uppercase border-start"
                            :class="{ active: trangThaiDonHangParams === 'Đang giao xe' }"
                            >Đang giao xe</router-link
                        >
                        <router-link
                            :to="{
                                name: 'DanhSachDonHangView',
                                params: { trangThaiDonHang: 'Đã giao xe' }
                            }"
                            class="btn btn-primary text-uppercase border-start"
                            :class="{ active: trangThaiDonHangParams === 'Đã giao xe' }"
                            >Đã giao xe</router-link
                        >
                        <router-link
                            :to="{
                                name: 'DanhSachDonHangView',
                                params: { trangThaiDonHang: 'Đã trả xe' }
                            }"
                            class="btn btn-primary text-uppercase border-start"
                            :class="{ active: trangThaiDonHangParams === 'Đã trả xe' }"
                            >Đã trả xe</router-link
                        >
                        <router-link
                            :to="{
                                name: 'DanhSachDonHangView',
                                params: { trangThaiDonHang: 'Đã hủy' }
                            }"
                            class="btn btn-primary text-uppercase border-start"
                            :class="{ active: trangThaiDonHangParams === 'Đã hủy' }"
                            >Đã hủy</router-link
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
                        <h5 class="card-title mb-0">Danh sách đơn hàng</h5>
                    </div>
                    <div class="card-header col-xl-6 text-end"></div>
                </div>
                <table class="table table-hover my-0">
                    <thead>
                        <tr>
                            <th>STT</th>
                            <th>Mã đơn hàng</th>
                            <th>Ngày tạo</th>
                            <th>Khách hàng</th>
                            <th>Nhân viên giao xe</th>
                            <th>Trạng thái đơn hàng</th>
                            <th
                                v-if="
                                    trangThaiDonHangParams === 'Chờ xử lý' ||
                                    trangThaiDonHangParams === 'Đã trả xe'
                                "
                            >
                                Thao tác
                            </th>
                        </tr>
                    </thead>
                    <tbody>
                        <tr v-if="paginatedDonHangs.length === 0">
                            <td colspan="6" class="text-center text-muted fst-italic">
                                Không có đơn hàng nào
                            </td>
                        </tr>
                        <tr
                            v-for="(item, index) in paginatedDonHangs"
                            :key="(item as any).donHang.maDonHang"
                        >
                            <td>
                                {{ index + 1 }}
                            </td>
                            <td>
                                {{ (item as any).donHang.maDonHang }}
                            </td>
                            <td>
                                {{
                                    (item as any).donHang.ngayTao
                                        ? formatDateTime(String((item as any).donHang.ngayTao))
                                        : null
                                }}
                            </td>
                            <td>
                                {{
                                    tenKhachHangs[String((item as any).donHang.maKhachHang)] ||
                                    'Đang tải...'
                                }}
                            </td>
                            <td>
                                {{ tenNhanViens[String((item as any).donHang.maNhanVien)] ?? '' }}
                            </td>
                            <td>{{ (item as any).donHang.trangThaiDonHang }}</td>
                            <td>
                                <router-link
                                    v-if="trangThaiDonHangParams === 'Chờ xử lý'"
                                    :to="{
                                        name: 'ChiTietDonHangView',
                                        params: {
                                            trangThaiDonHang: trangThaiDonHangParams,
                                            maDonHang: String((item as any).donHang.maDonHang) }
                                    }"
                                >
                                    <i class="fas fa-recycle text-warning" title="Xử lý"></i>
                                </router-link>
                                <router-link
                                    v-if="trangThaiDonHangParams === 'Đã trả xe'"
                                    :to="{
                                        name: 'ChiTietDonHangView',
                                        params: {
                                            trangThaiDonHang: trangThaiDonHangParams,
                                            maDonHang: String((item as any).donHang.maDonHang) }
                                    }"
                                >
                                    <i class="fas fa-recycle text-warning" title="Xử lý"></i>
                                </router-link>
                            </td>
                        </tr>
                    </tbody>
                </table>
            </div>
        </div>
        <PaginationComponent
            v-if="paginatedDonHangs.length > 0"
            class="text-center mt-3"
            :currentPage="currentPage"
            :totalPages="totalPages"
            :totalElements="totalElements"
            :pageSize="pageSize"
            @pageChanged="onChangePage"
        />
    </div>
    <PopupLoading :isLoading="isLoadingPage" />
</template>

<script lang="ts">
    import PaginationComponent from '@/components/dungchung/PaginationComponent.vue'
    import PopupLoading from '@/components/dungchung/PopupLoading.vue'
    import SearchComponent from '@/components/dungchung/SearchComponent.vue'
    import { useDate } from '@/composables/useDate'
    import { useDateTime } from '@/composables/useDateTime'
    import { getChiTietDonHangsByMaChuCuaHang } from '@/services/chiTietDonHangService'
    import { getKhachHangByMaNguoiDung } from '@/services/khachHangService'
    import { getMaNguoiDung } from '@/services/localStorageService'
    import { getNhanVienByMaNguoiDung } from '@/services/nhanVienService'
    import { computed, defineComponent, onMounted, ref, Ref, watch } from 'vue'
    import { useRoute, useRouter } from 'vue-router'

    export default defineComponent({
        name: 'DanhSachDonHang',
        components: {
            SearchComponent,
            PaginationComponent,
            PopupLoading
        },
        setup() {
            const totalPages = computed(() => Math.ceil(donHangs.value.length / pageSize.value))
            const route = useRoute()
            const router = useRouter()
            const currentPage = ref(Number(route.query.page) || 1) as Ref<number>
            const donHangs: Ref<Record<string, unknown>[]> = ref([])
            const tenKhachHangs = ref<Record<string, string>>({})
            const tenNhanViens = ref<Record<string, string>>({})
            const totalElements = ref() as Ref<number>
            const pageSize = ref(10) as Ref<number>
            const showDeletePopup = ref(false) as Ref<boolean>
            const khieuNaiToDelete = ref(null) as Ref<string | null>
            const keyword = ref('') as Ref<string>
            const trangThaiDonHangParams = String(route.params.trangThaiDonHang)
            const isLoadingPage = ref(true)
            const selectedSort = ref<'moiNhat' | 'cuNhat'>('moiNhat')

            const selectedSortLabel = computed(() => {
                return selectedSort.value === 'moiNhat' ? 'Mới nhất' : 'Cũ nhất'
            })

            const setSortOption = (option: 'moiNhat' | 'cuNhat') => {
                selectedSort.value = option
                fetchDonHangs()
            }

            const filteredDonHangs = computed(() => {
                if (!keyword.value.trim()) return donHangs.value

                // eslint-disable-next-line @typescript-eslint/no-explicit-any
                return donHangs.value.filter((item: any) => {
                    const maDonHang = String(item.donHang.maDonHang).toLowerCase()
                    const ngayTao = formatDate(item.donHang.ngayTao).toLowerCase()
                    const tenNhanVien = (
                        tenNhanViens.value[String(item.donHang.maNhanVien)] || ''
                    ).toLowerCase()
                    const tenKhachHang = (
                        tenKhachHangs.value[String(item.donHang.maKhachHang)] || ''
                    ).toLowerCase()

                    const kw = keyword.value.trim().toLowerCase()

                    return (
                        maDonHang.includes(kw) ||
                        ngayTao.includes(kw) ||
                        tenNhanVien.includes(kw) ||
                        tenKhachHang.includes(kw)
                    )
                })
            })

            const formatDate = (date: string) => {
                return useDate(date)
            }

            const formatDateTime = (dateTime: string) => {
                return useDateTime(dateTime)
            }

            const paginatedDonHangs = computed(() => {
                const start = (currentPage.value - 1) * pageSize.value
                return filteredDonHangs.value.slice(start, start + pageSize.value)
            })

            const fetchDonHangs = async () => {
                const result = await getChiTietDonHangsByMaChuCuaHang(
                    getMaNguoiDung(),
                    trangThaiDonHangParams
                )

                result.sort(
                    (
                        firstDate: { donHang: { ngayTao: string | number | Date } },
                        secondDate: { donHang: { ngayTao: string | number | Date } }
                    ) => {
                        const dateFirst = new Date(firstDate.donHang.ngayTao).getTime()
                        const dateSecond = new Date(secondDate.donHang.ngayTao).getTime()
                        return selectedSort.value === 'moiNhat'
                            ? dateSecond - dateFirst
                            : dateFirst - dateSecond
                    }
                )

                donHangs.value = result.filter(
                    (item: { donHang: { maDonHang: string } }, index: number, self: []) =>
                        index ===
                        self.findIndex(
                            (t: { donHang: { maDonHang: string } }) =>
                                t.donHang.maDonHang === item.donHang.maDonHang
                        )
                )

                for (const item of result) {
                    getTenKhachHang(String(item.donHang.maKhachHang))
                    getTenNhanVien(String(item.donHang.maNhanVien))
                }
            }

            onMounted(async () => {
                await Promise.all([fetchDonHangs()])
                isLoadingPage.value = false
            })

            const getTenKhachHang = async (maKhachHang: string) => {
                if (!tenKhachHangs.value[maKhachHang]) {
                    const khachHang = await getKhachHangByMaNguoiDung(maKhachHang)
                    tenKhachHangs.value[maKhachHang] = khachHang
                        ? khachHang.nguoiDung.hoVaTen
                        : 'Không xác định'
                }
                return tenKhachHangs.value[maKhachHang]
            }

            const getTenNhanVien = async (maNhanVien: string) => {
                if (!tenNhanViens.value[maNhanVien]) {
                    const nhanVien = await getNhanVienByMaNguoiDung(maNhanVien)
                    tenNhanViens.value[maNhanVien] = nhanVien ? nhanVien.nguoiDung.hoVaTen : ''
                }
                return tenNhanViens.value[maNhanVien]
            }

            watch(currentPage, (newPage) => {
                router.replace({ query: { ...route.query, page: newPage.toString() } })
                fetchDonHangs()
            })

            watch(
                () => route.params.trangThaiDonHang,
                () => {
                    window.location.reload()
                }
            )

            const onChangePage = (page: number) => {
                currentPage.value = page
            }

            return {
                isLoadingPage,
                formatDateTime,
                paginatedDonHangs,
                currentPage,
                totalPages,
                totalElements,
                pageSize,
                onChangePage,
                showDeletePopup,
                khieuNaiToDelete,
                tenKhachHangs,
                tenNhanViens,
                keyword,
                trangThaiDonHangParams,
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
