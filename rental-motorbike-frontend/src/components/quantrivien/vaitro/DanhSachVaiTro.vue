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
                        <tr v-if="paginatedVaiTros.length === 0">
                            <td colspan="6" class="text-center text-muted fst-italic">
                                Không có vai trò nào
                            </td>
                        </tr>
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
            v-if="paginatedVaiTros.length > 0"
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
    import { getVaiTros } from '@/services/vaiTroService'
    import { computed, defineComponent, onMounted, ref, Ref, watch } from 'vue'
    import { useRoute, useRouter } from 'vue-router'

    export default defineComponent({
        name: 'DanhSacVaiTro',
        components: {
            SearchComponent,
            PaginationComponent,
            PopupLoading
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
            const isLoadingPage = ref(true)

            const filteredVaiTros = computed(() => {
                if (!keyword.value.trim()) return vaiTros.value

                // eslint-disable-next-line @typescript-eslint/no-explicit-any
                return vaiTros.value.filter((item: any) => {
                    const maVaiTro = String(item.maVaiTro).toLowerCase()
                    const tenVaiTro = String(item.tenVaiTro).toLowerCase()

                    const kw = keyword.value.trim().toLowerCase()

                    return maVaiTro.includes(kw) || tenVaiTro.includes(kw)
                })
            })

            const fetchVaiTros = async () => {
                const result = await getVaiTros()
                vaiTros.value = result
            }

            const paginatedVaiTros = computed(() => {
                const start = (currentPage.value - 1) * pageSize.value
                return filteredVaiTros.value.slice(start, start + pageSize.value)
            })

            watch(currentPage, (newPage) => {
                router.replace({ query: { ...route.query, page: newPage.toString() } })
                fetchVaiTros()
            })

            const onChangePage = (page: number) => {
                currentPage.value = page
            }

            onMounted(async () => {
                await Promise.all([fetchVaiTros()])
                isLoadingPage.value = false
            })

            return {
                isLoadingPage,
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
