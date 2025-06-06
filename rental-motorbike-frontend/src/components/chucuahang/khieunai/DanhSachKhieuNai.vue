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
                        <h5 class="card-title mb-0">Danh sách khiếu nại</h5>
                    </div>
                    <div class="card-header col-xl-6 text-end">
                        <!-- <router-link class="text-success" :to="{ name: 'ThemMoiKhieuNaiView' }">
                            <i class="fas fa-plus-circle"></i>
                            <span class="ps-1">Thêm mới</span>
                        </router-link> -->
                    </div>
                </div>
                <table class="table table-hover my-0">
                    <thead>
                        <tr>
                            <th>STT</th>
                            <th>Mã khiếu nại</th>
                            <th>Nội dung</th>
                            <th>Loại khiếu nại</th>
                            <th>Ngày khiếu nại</th>
                            <th>Trạng thái xử lý</th>
                            <th>Thao tác</th>
                        </tr>
                    </thead>
                    <tbody>
                        <tr v-if="paginatedKhieuNais.length === 0">
                            <td colspan="6" class="text-center text-muted fst-italic">
                                Không có khiếu nại nào
                            </td>
                        </tr>
                        <tr
                            v-for="(item, index) in paginatedKhieuNais"
                            :key="(item as any).khieuNai.maKhieuNai"
                        >
                            <td>
                                <router-link
                                    :to="{
                                        name: 'XuLyKhieuNaiView',
                                        params: { maKhieuNai: String((item as any).khieuNai.maKhieuNai) }
                                    }"
                                >
                                    {{ index + 1 }}</router-link
                                >
                            </td>
                            <td>
                                <router-link
                                    :to="{
                                        name: 'XuLyKhieuNaiView',
                                        params: { maKhieuNai: String((item as any).khieuNai.maKhieuNai) }
                                    }"
                                >
                                    {{ (item as any).khieuNai.maKhieuNai }}</router-link
                                >
                            </td>
                            <td>
                                <router-link
                                    :to="{
                                        name: 'XuLyKhieuNaiView',
                                        params: { maKhieuNai: String((item as any).khieuNai.maKhieuNai) }
                                    }"
                                >
                                    {{ (item as any).khieuNai.noiDungKhieuNai }}</router-link
                                >
                            </td>
                            <td>
                                <router-link
                                    :to="{
                                        name: 'XuLyKhieuNaiView',
                                        params: { maKhieuNai: String((item as any).khieuNai.maKhieuNai) }
                                    }"
                                >
                                    {{
                                        tenLoaiKhieuNais[
                                            String((item as any).khieuNai.maLoaiKhieuNai)
                                        ] || 'Đang tải...'
                                    }}</router-link
                                >
                            </td>
                            <td>
                                <router-link
                                    :to="{
                                        name: 'XuLyKhieuNaiView',
                                        params: { maKhieuNai: String((item as any).khieuNai.maKhieuNai) }
                                    }"
                                >
                                    {{
                                        (item as any).khieuNai.ngayKhieuNai
                                            ? formatDateTime(
                                                  String((item as any).khieuNai.ngayKhieuNai)
                                              )
                                            : null
                                    }}</router-link
                                >
                            </td>
                            <td>
                                <router-link
                                    :to="{
                                        name: 'XuLyKhieuNaiView',
                                        params: { maKhieuNai: String((item as any).khieuNai.maKhieuNai) }
                                    }"
                                    >{{ (item as any).khieuNai.trangThaiXuLy }}</router-link
                                >
                            </td>
                            <td>
                                <router-link
                                    :to="{
                                        name: 'XuLyKhieuNaiView',
                                        params: { maKhieuNai: String((item as any).khieuNai.maKhieuNai) }
                                    }"
                                >
                                    <i
                                        v-if="String((item as any).khieuNai.trangThaiXuLy) === 'Chờ xử lý'"
                                        class="fas fa-recycle text-warning"
                                        title="Xử lý"
                                    ></i>
                                </router-link>
                            </td>
                        </tr>
                    </tbody>
                </table>
            </div>
        </div>
        <PaginationComponent
            v-if="paginatedKhieuNais.length > 0"
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
    import { getKhieuNaisByChuCuaHang } from '@/services/khieuNaiService'
    import { getLoaiKhieuNaiByMaLoaiKhieuNai } from '@/services/loaiKhieuNaiService'
    import { getMaNguoiDung } from '@/services/localStorageService'
    import { computed, defineComponent, onMounted, ref, Ref, watch } from 'vue'
    import { useRoute, useRouter } from 'vue-router'

    export default defineComponent({
        name: 'DanhSachKhieuNai',
        components: {
            SearchComponent,
            PaginationComponent,
            PopupLoading
        },
        setup() {
            const totalPages = computed(() => Math.ceil(khieuNais.value.length / pageSize.value))
            const route = useRoute()
            const router = useRouter()
            const currentPage = ref(Number(route.query.page) || 1) as Ref<number>
            const khieuNais: Ref<Record<string, unknown>[]> = ref([])
            const tenLoaiKhieuNais = ref<Record<string, string>>({})
            const totalElements = ref() as Ref<number>
            const pageSize = ref(10) as Ref<number>
            const keyword = ref('') as Ref<string>
            const isLoadingPage = ref(true)
            const selectedSort = ref<'moiNhat' | 'cuNhat'>('moiNhat')

            const selectedSortLabel = computed(() => {
                return selectedSort.value === 'moiNhat' ? 'Mới nhất' : 'Cũ nhất'
            })

            const setSortOption = (option: 'moiNhat' | 'cuNhat') => {
                selectedSort.value = option
                fetchKhieuNais()
            }

            const filteredKhieuNais = computed(() => {
                if (!keyword.value.trim()) return khieuNais.value

                // eslint-disable-next-line @typescript-eslint/no-explicit-any
                return khieuNais.value.filter((item: any) => {
                    const maKhieuNai = String(item.khieuNai.maKhieuNai).toLowerCase()
                    const noiDungKhieuNai = String(item.khieuNai.noiDungKhieuNai).toLowerCase()
                    const ngayKhieuNai = formatDate(item.khieuNai.ngayKhieuNai).toLowerCase()
                    const trangThaiXuLy = String(item.khieuNai.trangThaiXuLy).toLowerCase()
                    const tenLoaiKhieuNai = (
                        tenLoaiKhieuNais.value[String(item.khieuNai.maLoaiKhieuNai)] || ''
                    ).toLowerCase()

                    const kw = keyword.value.trim().toLowerCase()

                    return (
                        maKhieuNai.includes(kw) ||
                        noiDungKhieuNai.includes(kw) ||
                        ngayKhieuNai.includes(kw) ||
                        trangThaiXuLy.includes(kw) ||
                        tenLoaiKhieuNai.includes(kw)
                    )
                })
            })

            const formatDate = (date: string) => {
                return useDate(date)
            }

            const formatDateTime = (dateTime: string) => {
                return useDateTime(dateTime)
            }
            const paginatedKhieuNais = computed(() => {
                const start = (currentPage.value - 1) * pageSize.value
                return filteredKhieuNais.value.slice(start, start + pageSize.value)
            })

            const fetchKhieuNais = async () => {
                const result = await getKhieuNaisByChuCuaHang(getMaNguoiDung())

                result.sort(
                    (
                        firstDate: { khieuNai: { ngayKhieuNai: string | number | Date } },
                        secondDate: { khieuNai: { ngayKhieuNai: string | number | Date } }
                    ) => {
                        const dateFirst = new Date(firstDate.khieuNai.ngayKhieuNai).getTime()
                        const dateSecond = new Date(secondDate.khieuNai.ngayKhieuNai).getTime()
                        return selectedSort.value === 'moiNhat'
                            ? dateSecond - dateFirst
                            : dateFirst - dateSecond
                    }
                )

                khieuNais.value = result.filter(
                    (item: { khieuNai: { maKhieuNai: string } }, index: number, self: []) =>
                        index ===
                        self.findIndex(
                            (t: { khieuNai: { maKhieuNai: string } }) =>
                                t.khieuNai.maKhieuNai === item.khieuNai.maKhieuNai
                        )
                )

                for (const item of result) {
                    getTenLoaiKhieuNai(String(item.khieuNai.maLoaiKhieuNai))
                }
            }

            onMounted(async () => {
                await Promise.all([fetchKhieuNais()])
                isLoadingPage.value = false
            })

            const getTenLoaiKhieuNai = async (maLoaiKhieuNai: string) => {
                if (!tenLoaiKhieuNais.value[maLoaiKhieuNai]) {
                    const loaiKhieuNai = await getLoaiKhieuNaiByMaLoaiKhieuNai(maLoaiKhieuNai)
                    tenLoaiKhieuNais.value[maLoaiKhieuNai] = loaiKhieuNai
                        ? loaiKhieuNai.tenLoaiKhieuNai
                        : 'Không xác định'
                }
                return tenLoaiKhieuNais.value[maLoaiKhieuNai]
            }

            watch(currentPage, (newPage) => {
                router.replace({ query: { ...route.query, page: newPage.toString() } })
                fetchKhieuNais()
            })

            const onChangePage = (page: number) => {
                currentPage.value = page
            }

            return {
                isLoadingPage,
                formatDateTime,
                paginatedKhieuNais,
                currentPage,
                totalPages,
                totalElements,
                pageSize,
                onChangePage,
                tenLoaiKhieuNais,
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
