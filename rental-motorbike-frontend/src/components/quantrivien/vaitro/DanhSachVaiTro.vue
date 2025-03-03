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
                        <h5 class="card-title mb-0">Danh sách vai trò</h5>
                    </div>
                    <div class="card-header col-xl-6 text-end">
                        <router-link class="text-success" :to="{ name: 'ThemMoiVaiTroView' }">
                            <i class="fas fa-plus-circle"></i>
                            <span class="ps-1">Thêm mới</span>
                        </router-link>
                    </div>
                </div>
                <table class="table table-hover my-0">
                    <thead>
                        <tr>
                            <th>STT</th>
                            <th>Mã vai trò</th>
                            <th>Tên vai trò</th>
                            <th>Thao tác</th>
                        </tr>
                    </thead>
                    <tbody>
                        <tr
                            v-for="(vaiTro, index) in paginatedVaiTros"
                            :key="typeof vaiTro.maVaiTro === 'string' ? vaiTro.maVaiTro : undefined"
                        >
                            <td>
                                {{ index + 1 }}
                            </td>
                            <td>
                                {{ vaiTro.maVaiTro }}
                            </td>
                            <td>
                                {{ vaiTro.tenVaiTro }}
                            </td>
                            <td>
                                <router-link
                                    :to="{
                                        name: 'CapNhatVaiTroView',
                                        params: {
                                            maVaiTro: String(vaiTro.maVaiTro)
                                        }
                                    }"
                                >
                                    <i class="far fa-edit text-success" title="Cập nhật"></i
                                ></router-link>
                            </td>
                        </tr>
                    </tbody>
                </table>
            </div>
        </div>
        <PaginationComponent
            class="text-center mt-3"
            :currentPage="currentPage"
            :totalPages="totalPages"
            :totalElements="totalElements"
            :pageSize="pageSize"
            @pageChanged="onChangePage"
        />
    </div>
</template>

<script lang="ts">
    import PaginationComponent from '@/components/dungchung/PaginationComponent.vue'
    import SearchComponent from '@/components/dungchung/SearchComponent.vue'
    import { getVaiTros } from '@/services/quantrivien/vaiTroService'
    import { computed, defineComponent, onMounted, ref, Ref, watch } from 'vue'
    import { useRoute, useRouter } from 'vue-router'

    export default defineComponent({
        name: 'DanhSacVaiTro',
        components: {
            SearchComponent,
            PaginationComponent
        },
        setup() {
            const totalPages = computed(() => Math.ceil(vaiTros.value.length / pageSize.value))
            const route = useRoute()
            const router = useRouter()
            const currentPage = ref(Number(route.query.page) || 1) as Ref<number>
            const vaiTros: Ref<Record<string, unknown>[]> = ref([])
            const totalElements = ref() as Ref<number>
            const pageSize = ref(10) as Ref<number>
            const showDeletePopup = ref(false) as Ref<boolean>
            const keyword = ref('') as Ref<string>

            const fetcVaiTros = async () => {
                const result = await getVaiTros()
                vaiTros.value = result
            }

            const paginatedVaiTros = computed(() => {
                const start = (currentPage.value - 1) * pageSize.value
                return vaiTros.value.slice(start, start + pageSize.value)
            })

            watch(currentPage, (newPage) => {
                router.replace({ query: { ...route.query, page: newPage.toString() } })
                fetcVaiTros()
            })

            const onChangePage = (page: number) => {
                currentPage.value = page
            }

            onMounted(() => {
                fetcVaiTros()
            })

            return {
                paginatedVaiTros,
                currentPage,
                totalPages,
                totalElements,
                pageSize,
                onChangePage,
                showDeletePopup,
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
