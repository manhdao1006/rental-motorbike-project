<template>
    <div class="container-fluid">
        <div class="col-12 col-lg-12 col-xxl-12 d-flex">
            <div class="card flex-fill">
                <div class="row">
                    <div class="col-6">
                        <SearchComponent class="w-100" v-model="keyword" />
                    </div>
                </div>

                <div class="row justify-content-evenly">
                    <div class="card-header col-xl-6">
                        <h5 class="card-title mb-0">Danh sách danh mục xe</h5>
                    </div>
                    <div class="card-header col-xl-6 text-end">
                        <router-link class="text-success" :to="{ name: 'ThemMoiDanhMucXeView' }">
                            <i class="fas fa-plus-circle"></i>
                            <span class="ps-1">Thêm mới</span>
                        </router-link>
                    </div>
                </div>
                <table class="table table-hover my-0">
                    <thead>
                        <tr>
                            <th>STT</th>
                            <th>Mã danh mục xe</th>
                            <th>Tên danh mục xe</th>
                            <th>Thao tác</th>
                        </tr>
                    </thead>
                    <tbody>
                        <tr v-if="paginatedDanhMucXes.length === 0">
                            <td colspan="6" class="text-center text-muted fst-italic">
                                Không có danh mục xe nào
                            </td>
                        </tr>
                        <tr
                            v-for="(danhMucXe, index) in paginatedDanhMucXes"
                            :key="
                                typeof danhMucXe.maDanhMucXe === 'string'
                                    ? danhMucXe.maDanhMucXe
                                    : undefined
                            "
                        >
                            <td>
                                {{ index + 1 }}
                            </td>
                            <td>
                                {{ danhMucXe.maDanhMucXe }}
                            </td>
                            <td>
                                {{ danhMucXe.tenDanhMucXe }}
                            </td>
                            <td>
                                <router-link
                                    :to="{
                                        name: 'CapNhatDanhMucXeView',
                                        params: {
                                            maDanhMucXe: String(danhMucXe.maDanhMucXe)
                                        }
                                    }"
                                >
                                    <i class="far fa-edit text-success" title="Cập nhật"></i
                                ></router-link>
                                |
                                <button
                                    @click="showConfirmPopup(String(danhMucXe.maDanhMucXe))"
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
            v-if="paginatedDanhMucXes.length > 0"
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
    import { deleteDanhMucXe, getDanhMucXes } from '@/services/danhMucXeService'
    import { computed, defineComponent, onMounted, ref, Ref, watch } from 'vue'
    import { useRoute, useRouter } from 'vue-router'

    export default defineComponent({
        name: 'DanhSacDanhMucXe',
        components: {
            SearchComponent,
            PaginationComponent,
            PopupDelete,
            PopupLoading
        },
        setup() {
            const totalPages = computed(() => Math.ceil(danhMucXes.value.length / pageSize.value))
            const route = useRoute()
            const router = useRouter()
            const currentPage = ref(Number(route.query.page) || 1) as Ref<number>
            const danhMucXes: Ref<Record<string, unknown>[]> = ref([])
            const totalElements = ref() as Ref<number>
            const pageSize = ref(10) as Ref<number>
            const showDeletePopup = ref(false) as Ref<boolean>
            const danhMucXeToDelete = ref(null) as Ref<string | null>
            const keyword = ref('') as Ref<string>
            const isLoadingPage = ref(true)

            const filteredDanhMucXes = computed(() => {
                if (!keyword.value.trim()) return danhMucXes.value

                // eslint-disable-next-line @typescript-eslint/no-explicit-any
                return danhMucXes.value.filter((item: any) => {
                    const maDanhMucXe = String(item.maDanhMucXe).toLowerCase()
                    const tenDanhMucXe = String(item.tenDanhMucXe).toLowerCase()

                    const kw = keyword.value.trim().toLowerCase()

                    return maDanhMucXe.includes(kw) || tenDanhMucXe.includes(kw)
                })
            })

            const fetcDanhMucXes = async () => {
                const result = await getDanhMucXes()
                danhMucXes.value = result
            }

            const paginatedDanhMucXes = computed(() => {
                const start = (currentPage.value - 1) * pageSize.value
                return filteredDanhMucXes.value.slice(start, start + pageSize.value)
            })

            watch(currentPage, (newPage) => {
                router.replace({ query: { ...route.query, page: newPage.toString() } })
                fetcDanhMucXes()
            })

            const onChangePage = (page: number) => {
                currentPage.value = page
            }

            const showConfirmPopup = (maDanhMucXe: unknown) => {
                if (typeof maDanhMucXe === 'string') {
                    danhMucXeToDelete.value = maDanhMucXe
                    showDeletePopup.value = true
                } else {
                    console.error('Lỗi: ', maDanhMucXe)
                }
            }

            const confirmDelete = async () => {
                if (danhMucXeToDelete.value) {
                    await deleteDanhMucXe(danhMucXeToDelete.value)
                    fetcDanhMucXes()
                }
            }

            onMounted(async () => {
                await Promise.all([fetcDanhMucXes()])
                isLoadingPage.value = false
            })

            return {
                isLoadingPage,
                paginatedDanhMucXes,
                currentPage,
                totalPages,
                totalElements,
                pageSize,
                onChangePage,
                showDeletePopup,
                danhMucXeToDelete,
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
