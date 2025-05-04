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
                        <h5 class="card-title mb-0">Danh sách loại khiếu nại</h5>
                    </div>
                    <div class="card-header col-xl-6 text-end">
                        <router-link class="text-success" :to="{ name: 'ThemMoiLoaiKhieuNaiView' }">
                            <i class="fas fa-plus-circle"></i>
                            <span class="ps-1">Thêm mới</span>
                        </router-link>
                    </div>
                </div>
                <table class="table table-hover my-0">
                    <thead>
                        <tr>
                            <th>STT</th>
                            <th>Mã loại khiếu nại</th>
                            <th>Tên loại khiếu nại</th>
                            <th>Thao tác</th>
                        </tr>
                    </thead>
                    <tbody>
                        <tr v-if="paginatedLoaiKhieuNais.length === 0">
                            <td colspan="6" class="text-center text-muted fst-italic">
                                Không có loại khiếu nại nào
                            </td>
                        </tr>
                        <tr
                            v-for="(loaiKhieuNai, index) in paginatedLoaiKhieuNais"
                            :key="
                                typeof loaiKhieuNai.maLoaiKhieuNai === 'string'
                                    ? loaiKhieuNai.maLoaiKhieuNai
                                    : undefined
                            "
                        >
                            <td>
                                {{ index + 1 }}
                            </td>
                            <td>
                                {{ loaiKhieuNai.maLoaiKhieuNai }}
                            </td>
                            <td>
                                {{ loaiKhieuNai.tenLoaiKhieuNai }}
                            </td>
                            <td>
                                <router-link
                                    :to="{
                                        name: 'CapNhatLoaiKhieuNaiView',
                                        params: {
                                            maLoaiKhieuNai: String(loaiKhieuNai.maLoaiKhieuNai)
                                        }
                                    }"
                                >
                                    <i class="far fa-edit text-success" title="Cập nhật"></i
                                ></router-link>
                                |
                                <button
                                    @click="showConfirmPopup(String(loaiKhieuNai.maLoaiKhieuNai))"
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
            v-if="totalElements > 0"
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
</template>

<script lang="ts">
    import PaginationComponent from '@/components/dungchung/PaginationComponent.vue'
    import PopupDelete from '@/components/dungchung/PopupDelete.vue'
    import SearchComponent from '@/components/dungchung/SearchComponent.vue'
    import { deleteLoaiKhieuNai, getLoaiKhieuNais } from '@/services/loaiKhieuNaiService'
    import { computed, defineComponent, onMounted, ref, Ref, watch } from 'vue'
    import { useRoute, useRouter } from 'vue-router'

    export default defineComponent({
        name: 'DanhSachLoaiKhieuNai',
        components: {
            SearchComponent,
            PaginationComponent,
            PopupDelete
        },
        setup() {
            const totalPages = computed(() =>
                Math.ceil(loaiKhieuNais.value.length / pageSize.value)
            )
            const route = useRoute()
            const router = useRouter()
            const currentPage = ref(Number(route.query.page) || 1) as Ref<number>
            const loaiKhieuNais: Ref<Record<string, unknown>[]> = ref([])
            const totalElements = ref() as Ref<number>
            const pageSize = ref(10) as Ref<number>
            const showDeletePopup = ref(false) as Ref<boolean>
            const loaiKhieuNaiToDelete = ref(null) as Ref<string | null>
            const keyword = ref('') as Ref<string>

            const fetchLoaiKhieuNais = async () => {
                const result = await getLoaiKhieuNais()
                loaiKhieuNais.value = result
            }

            const paginatedLoaiKhieuNais = computed(() => {
                const start = (currentPage.value - 1) * pageSize.value
                return loaiKhieuNais.value.slice(start, start + pageSize.value)
            })

            watch(currentPage, (newPage) => {
                router.replace({ query: { ...route.query, page: newPage.toString() } })
                fetchLoaiKhieuNais()
            })

            const onChangePage = (page: number) => {
                currentPage.value = page
            }

            const showConfirmPopup = (maLoaiKhieuNai: unknown) => {
                if (typeof maLoaiKhieuNai === 'string') {
                    loaiKhieuNaiToDelete.value = maLoaiKhieuNai
                    showDeletePopup.value = true
                } else {
                    console.error('Lỗi: ', maLoaiKhieuNai)
                }
            }

            const confirmDelete = async () => {
                if (loaiKhieuNaiToDelete.value) {
                    await deleteLoaiKhieuNai(loaiKhieuNaiToDelete.value)
                    fetchLoaiKhieuNais()
                }
            }

            onMounted(() => {
                fetchLoaiKhieuNais()
            })

            return {
                paginatedLoaiKhieuNais,
                currentPage,
                totalPages,
                totalElements,
                pageSize,
                onChangePage,
                showDeletePopup,
                loaiKhieuNaiToDelete,
                showConfirmPopup,
                confirmDelete,
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
