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
                                Mặc định
                            </a>

                            <ul class="dropdown-menu" aria-labelledby="dropdownMenuLink">
                                <li>
                                    <button class="dropdown-item">Người dùng</button>
                                </li>
                                <li>
                                    <button class="dropdown-item">Quản trị viên</button>
                                </li>
                            </ul>
                        </div>
                    </div>
                </div>

                <div class="row justify-content-evenly">
                    <div class="card-header col-xl-6">
                        <h5 class="card-title mb-0">Danh sách nhân viên</h5>
                    </div>
                    <div class="card-header col-xl-6 text-end">
                        <router-link
                            class="text-success"
                            :to="{ name: 'ThemMoiNhanVienCuaHangView' }"
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
                            <th>Mã nhân viên</th>
                            <th>Họ và tên</th>
                            <th>Email</th>
                            <th>Số điện thoại</th>
                            <th>Giới tính</th>
                            <th>Trạng thái hoạt động</th>
                            <th>Thao tác</th>
                        </tr>
                    </thead>
                    <tbody>
                        <tr v-if="paginatedNhanViens.length === 0">
                            <td colspan="6" class="text-center text-muted fst-italic">
                                Không có nhân viên nào
                            </td>
                        </tr>
                        <tr
                            v-for="(item, index) in paginatedNhanViens"
                            :key="(item as any).nhanVien.maNhanVien"
                        >
                            <td>
                                {{ index + 1 }}
                            </td>
                            <td>
                                {{ (item as any).nhanVien.maNhanVien }}
                            </td>
                            <td>
                                {{ (item as any).nguoiDung.hoVaTen }}
                            </td>
                            <td>
                                {{ (item as any).nguoiDung.email }}
                            </td>
                            <td>
                                {{ (item as any).nguoiDung.soDienThoai }}
                            </td>
                            <td>
                                {{ (item as any).nguoiDung.gioiTinh }}
                            </td>
                            <td>
                                {{ (item as any).nguoiDung.trangThaiHoatDong }}
                            </td>
                            <td>
                                <router-link
                                    :to="{
                                        name: 'CapNhatNhanVienCuaHangView',
                                        params: { maNhanVien: String((item as any).nhanVien.maNhanVien) }
                                    }"
                                >
                                    <i class="far fa-edit text-success" title="Cập nhật"></i
                                ></router-link>
                                |
                                <button
                                    @click="
                                        showConfirmPopup(String((item as any).nhanVien.maNhanVien))
                                    "
                                    class="border-0 p-0 bg-transparent"
                                >
                                    <i class="fas fa-trash-alt text-danger" title="Xóa"></i>
                                </button>
                                |

                                <button
                                    @click="
                                        showConfirmBanPopup(
                                            String((item as any).nhanVien.maNhanVien)
                                        )
                                    "
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
            v-if="paginatedNhanViens.length > 0"
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
    import { getMaNguoiDung } from '@/services/localStorageService'
    import {
        banNhanVien,
        deleteNhanVien,
        getNhanViensByChuCuaHang
    } from '@/services/nhanVienService'
    import { computed, defineComponent, onMounted, ref, Ref, watch } from 'vue'
    import { useRoute, useRouter } from 'vue-router'
    export default defineComponent({
        name: 'DanhSachNhanVienCuaHang',
        components: {
            SearchComponent,
            PaginationComponent,
            PopupDelete,
            PopupBan,
            PopupLoading
        },
        setup() {
            const totalPages = computed(() => Math.ceil(nhanViens.value.length / pageSize.value))
            const route = useRoute()
            const router = useRouter()
            const currentPage = ref(Number(route.query.page) || 1) as Ref<number>
            const nhanViens: Ref<Record<string, unknown>[]> = ref([])
            const tenChuCuaHangs = ref<Record<string, string>>({})
            const totalElements = ref() as Ref<number>
            const pageSize = ref(10) as Ref<number>
            const showDeletePopup = ref(false) as Ref<boolean>
            const showBanPopup = ref(false) as Ref<boolean>
            const nhanVienToDelete = ref(null) as Ref<string | null>
            const nhanVienToBan = ref(null) as Ref<string | null>
            const keyword = ref('') as Ref<string>
            const isLoadingPage = ref(true)

            const fetchNhanViens = async () => {
                const result = await getNhanViensByChuCuaHang(getMaNguoiDung())
                nhanViens.value = result
            }

            const paginatedNhanViens = computed(() => {
                const start = (currentPage.value - 1) * pageSize.value
                return nhanViens.value.slice(start, start + pageSize.value)
            })

            watch(currentPage, (newPage) => {
                router.replace({ query: { ...route.query, page: newPage.toString() } })
                fetchNhanViens()
            })

            const onChangePage = (page: number) => {
                currentPage.value = page
            }

            const showConfirmPopup = (maNguoiDung: unknown) => {
                if (typeof maNguoiDung === 'string') {
                    nhanVienToDelete.value = maNguoiDung
                    showDeletePopup.value = true
                } else {
                    console.error('Lỗi: ', maNguoiDung)
                }
            }

            const showConfirmBanPopup = (maNguoiDung: unknown) => {
                if (typeof maNguoiDung === 'string') {
                    nhanVienToBan.value = maNguoiDung
                    showBanPopup.value = true
                } else {
                    console.error('Lỗi: ', maNguoiDung)
                }
            }

            const confirmDelete = async () => {
                if (nhanVienToDelete.value) {
                    await deleteNhanVien(nhanVienToDelete.value)
                    fetchNhanViens()
                }
            }

            const confirmBan = async () => {
                if (nhanVienToBan.value) {
                    await banNhanVien(nhanVienToBan.value)
                    fetchNhanViens()
                }
            }

            onMounted(async () => {
                await Promise.all([fetchNhanViens()])
                isLoadingPage.value = false
            })

            return {
                isLoadingPage,
                paginatedNhanViens,
                currentPage,
                totalPages,
                totalElements,
                pageSize,
                onChangePage,
                showConfirmPopup,
                showConfirmBanPopup,
                confirmBan,
                confirmDelete,
                showDeletePopup,
                showBanPopup,
                nhanVienToDelete,
                tenChuCuaHangs,
                keyword
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
