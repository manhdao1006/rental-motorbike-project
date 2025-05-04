<template>
    <div class="container mt-3" style="font-family: Arial">
        <div class="row">
            <div class="col-3 bg-white">
                <div class="row mt-3">
                    <h6 class="fw-bold mb-2">Danh mục xe</h6>
                    <div>
                        <ul class="list-unstyled mb-0">
                            <li v-for="item in danhMucXes" :key="(item as any).maDanhMucXe">
                                <label class="form-check-label">
                                    <input
                                        class="form-check-input me-2"
                                        type="checkbox"
                                        :value="item.maDanhMucXe"
                                        v-model="danhMucDaChon"
                                    />
                                    {{ item.tenDanhMucXe }}
                                </label>
                            </li>
                        </ul>
                    </div>
                </div>
                <div class="row mt-3">
                    <h6 class="fw-bold mb-2">Loại xe</h6>
                    <div>
                        <ul class="list-unstyled mb-0">
                            <li v-for="loai in loaiXes" :key="loai">
                                <label class="form-check-label">
                                    <input
                                        class="form-check-input me-2"
                                        type="checkbox"
                                        :value="loai"
                                        v-model="loaiXeDaChon"
                                    />
                                    {{ loai }}
                                </label>
                            </li>
                        </ul>
                    </div>
                </div>
                <div class="row mt-3">
                    <h6 class="fw-bold mb-2">Giá thuê</h6>

                    <div class="d-flex align-items-center gap-2">
                        <input
                            type="number"
                            class="form-control shadow-sm"
                            placeholder="Từ"
                            v-model.number="giaTu"
                            min="0"
                        />
                        <span>-</span>
                        <input
                            type="number"
                            class="form-control shadow-sm"
                            placeholder="Đến"
                            v-model.number="giaDen"
                            :min="giaTu ?? 0"
                        />
                    </div>
                    <div class="text-center mt-3">
                        <button class="btn btn-primary shadow-sm me-5" @click="locTuKhoa">
                            Tìm
                        </button>
                        <button class="btn btn-primary btn-cart shadow-sm" @click="huyLoc">
                            Huỷ
                        </button>
                    </div>
                </div>
            </div>
            <div class="col-9">
                <div class="row ms-0 mb-3">
                    <div class="col-9 row">
                        <div
                            class="col-2 btn bg-white shadow-sm"
                            :class="{ active: activeFilter === 'tatCa' }"
                            @click="setActiveFilter('tatCa')"
                        >
                            Tất cả
                        </div>
                        <div
                            class="col-2 btn bg-white shadow-sm ms-2"
                            :class="{ active: activeFilter === 'moiNhat' }"
                            @click="setActiveFilter('moiNhat')"
                        >
                            Mới nhất
                        </div>
                        <div
                            class="col-2 btn bg-white shadow-sm ms-2"
                            :class="{ active: activeFilter === 'caoNhat' }"
                            @click="setActiveFilter('caoNhat')"
                        >
                            Cao nhất
                        </div>
                        <div
                            class="col-2 btn bg-white shadow-sm ms-2"
                            :class="{ active: activeFilter === 'thapNhat' }"
                            @click="setActiveFilter('thapNhat')"
                        >
                            Thấp nhất
                        </div>
                    </div>
                </div>
                <div class="row">
                    <div
                        v-if="paginatedXeMays.length === 0"
                        class="text-center text-muted fst-italic"
                    >
                        Không có xe máy nào
                    </div>
                    <div
                        v-for="item in paginatedXeMays"
                        :key="(item as any).xeMay.maXeMay"
                        class="col-4 mb-4"
                    >
                        <router-link
                            :to="{
                                name: 'ChiTietXeMayTrangChuView',
                                params: { maXeMay: (item as any).xeMay.maXeMay }
                            }"
                        >
                            <div class="card">
                                <img
                                    :src="(item as any).xeMay.anhXeMays[0].duongDan"
                                    class="pt-3 ps-3 pe-3"
                                    :alt="(item as any).xeMay.anhXeMays[0].tenAnh"
                                    height="240px"
                                />
                                <div class="card-body">
                                    <div
                                        class="card-title card-heading limited-width-slide"
                                        :title="(item as any).xeMay.tenXe"
                                    >
                                        {{ (item as any).xeMay.tenXe }}
                                    </div>
                                    <div
                                        class="card-text card-content limited-width-slide"
                                        :title="(item as any).xeMay.loaiXe"
                                    >
                                        {{ (item as any).xeMay.loaiXe }} -
                                        {{ (item as any).danhMucXe.tenDanhMucXe }}
                                    </div>
                                    <div class="card-text card-price">
                                        {{ formattedGiaThue((item as any).xeMay.giaThue) }} VNĐ/Ngày
                                    </div>
                                    <div
                                        class="card-text card-address limited-width-slide"
                                        :title="tenQuanHuyens[(item as any).chuCuaHang.maPhuongXa]"
                                    >
                                        {{ tenQuanHuyens[(item as any).chuCuaHang.maPhuongXa] }} -
                                        {{ tenPhuongXas[(item as any).chuCuaHang.maPhuongXa] }}
                                    </div>
                                </div>
                            </div>
                        </router-link>
                    </div>
                </div>
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
    import { getPhuongXaByMaPhuongXa, getQuanHuyenByMaPhuongXa } from '@/services/authService'
    import { getDanhMucXes } from '@/services/danhMucXeService'
    import { getXeMays, getXeMaysByMaQuanHuyen } from '@/services/xeMayService'
    import { computed, defineComponent, onMounted, Ref, ref, watch } from 'vue'
    import { useRoute, useRouter } from 'vue-router'
    import PaginationComponent from '../dungchung/PaginationComponent.vue'

    export default defineComponent({
        name: 'DanhSachXeMayTheoQuan',
        components: {
            PaginationComponent
        },
        setup() {
            const route = useRoute()
            const router = useRouter()
            const currentPage = ref(Number(route.query.page) || 1) as Ref<number>
            const totalPages = computed(() => Math.ceil(xeMays.value.length / pageSize.value))
            const totalElements = ref() as Ref<number>
            const pageSize = ref(12) as Ref<number>
            const xeMays = ref<Record<string, unknown>[]>([])
            const danhMucXes = ref<Record<string, unknown>[]>([])
            const tenQuanHuyens = ref<Record<string, string>>({})
            const tenPhuongXas = ref<Record<string, string>>({})
            const activeFilter: Ref<'default' | 'tatCa' | 'moiNhat' | 'caoNhat' | 'thapNhat'> =
                ref('default')
            const giaTu = ref<number | null>(null)
            const giaDen = ref<number | null>(null)
            const tatCaXeMays = ref<Record<string, unknown>[]>([])
            const danhMucDaChon = ref<string[]>([])
            const loaiXeDaChon = ref<string[]>([])
            const loaiXes = ['Xe số', 'Xe tay ga', 'Xe côn tay']
            const maQuanHuyen = ref<string>('')

            onMounted(() => {
                const param = route.params.maQuanHuyen
                if (typeof param === 'string') {
                    maQuanHuyen.value = param
                }
            })

            const locTuKhoa = () => {
                const tu = giaTu.value
                const den = giaDen.value

                xeMays.value = tatCaXeMays.value.filter((item) => {
                    const xeMay = item['xeMay'] as Record<string, unknown>

                    const maDanhMuc = xeMay['maDanhMucXe']
                    const loaiXe = xeMay['loaiXe']
                    const gia = xeMay['giaThue']

                    const thoaGia =
                        typeof gia === 'number' ? (!tu || gia >= tu) && (!den || gia <= den) : true

                    const thoaDanhMuc =
                        danhMucDaChon.value.length === 0 ||
                        danhMucDaChon.value.includes(String(maDanhMuc))

                    const thoaLoaiXe =
                        loaiXeDaChon.value.length === 0 ||
                        loaiXeDaChon.value.includes(String(loaiXe))

                    return thoaGia && thoaDanhMuc && thoaLoaiXe
                })
                router.replace({ name: 'DanhSachXeMayTheoQuanView' })
            }

            const huyLoc = () => {
                danhMucDaChon.value = []
                loaiXeDaChon.value = []
                giaTu.value = null
                giaDen.value = null

                xeMays.value = [...tatCaXeMays.value]
            }

            const formattedGiaThue = (giaThue: string) => {
                if (!giaThue) return '0'
                const numberGiaThue = Number(giaThue)
                return numberGiaThue.toLocaleString('vi-VN')
            }

            const fetchDanhMucXes = async () => {
                const response = await getDanhMucXes()
                danhMucXes.value = response
            }

            const fetchXeMays = async () => {
                let response: Record<string, unknown>[] = []

                if (activeFilter.value === 'tatCa') {
                    if (maQuanHuyen.value) {
                        router.replace({ path: '/danh-sach-xe' })
                    }
                    maQuanHuyen.value = ''
                    response = await getXeMays()
                } else {
                    if (maQuanHuyen.value) {
                        response = await getXeMaysByMaQuanHuyen(maQuanHuyen.value)
                    } else {
                        response = await getXeMays()
                    }

                    if (activeFilter.value === 'moiNhat') {
                        response.sort((a, b) => {
                            const aXeMay = a.xeMay as Record<string, unknown>
                            const bXeMay = b.xeMay as Record<string, unknown>
                            const aSo = parseInt(String(aXeMay.maXeMay).replace(/\D/g, ''), 10)
                            const bSo = parseInt(String(bXeMay.maXeMay).replace(/\D/g, ''), 10)
                            return bSo - aSo
                        })
                    } else if (activeFilter.value === 'caoNhat') {
                        response.sort((a, b) => {
                            const aXeMay = a.xeMay as Record<string, unknown>
                            const bXeMay = b.xeMay as Record<string, unknown>
                            return (bXeMay.giaThue as number) - (aXeMay.giaThue as number)
                        })
                    } else if (activeFilter.value === 'thapNhat') {
                        response.sort((a, b) => {
                            const aXeMay = a.xeMay as Record<string, unknown>
                            const bXeMay = b.xeMay as Record<string, unknown>
                            return (aXeMay.giaThue as number) - (bXeMay.giaThue as number)
                        })
                    }
                }

                tatCaXeMays.value = response
                xeMays.value = response

                for (const item of response) {
                    const chuCuaHang = item.chuCuaHang as Record<string, unknown>
                    const maPX = String(chuCuaHang.maPhuongXa)
                    getTenPhuongXa(maPX)
                    getTenQuanHuyen(maPX)
                }
            }

            const getTenPhuongXa = async (maPhuongXa: string) => {
                if (!tenPhuongXas.value[maPhuongXa]) {
                    const phuongXa = await getPhuongXaByMaPhuongXa(maPhuongXa)
                    tenPhuongXas.value[maPhuongXa] = phuongXa
                        ? phuongXa.tenPhuongXa
                        : 'Không xác định'
                }
                return tenPhuongXas.value[maPhuongXa]
            }

            const getTenQuanHuyen = async (maPhuongXa: string) => {
                if (!tenQuanHuyens.value[maPhuongXa]) {
                    const quanHuyen = await getQuanHuyenByMaPhuongXa(maPhuongXa)
                    tenQuanHuyens.value[maPhuongXa] = quanHuyen
                        ? quanHuyen.tenQuanHuyen
                        : 'Không xác định'
                }
                return tenQuanHuyens.value[maPhuongXa]
            }

            const paginatedXeMays = computed(() => {
                const start = (currentPage.value - 1) * pageSize.value
                return xeMays.value.slice(start, start + pageSize.value)
            })

            watch(currentPage, (newPage) => {
                router.replace({ query: { ...route.query, page: newPage.toString() } })
                fetchXeMays()
            })

            const onChangePage = (page: number) => {
                currentPage.value = page
            }

            const setActiveFilter = (
                filter: 'default' | 'tatCa' | 'moiNhat' | 'caoNhat' | 'thapNhat'
            ) => {
                activeFilter.value = filter
                if (activeFilter.value === 'tatCa') {
                    huyLoc()
                }
                fetchXeMays()
            }

            const handleChange = (event: Event) => {
                const selectElement = event.target as HTMLSelectElement | null
                if (selectElement) {
                    setActiveFilter(selectElement.value as 'caoNhat' | 'thapNhat')
                }
            }

            onMounted(() => {
                fetchDanhMucXes()
                fetchXeMays()
            })

            return {
                huyLoc,
                loaiXes,
                danhMucDaChon,
                loaiXeDaChon,
                handleChange,
                activeFilter,
                setActiveFilter,
                maQuanHuyen,
                danhMucXes,
                xeMays,
                paginatedXeMays,
                currentPage,
                totalPages,
                totalElements,
                pageSize,
                onChangePage,
                formattedGiaThue,
                tenPhuongXas,
                tenQuanHuyens,
                locTuKhoa,
                giaTu,
                giaDen
            }
        }
    })
</script>

<style>
    /* .btn.active {
        background-color: #ff8435 !important;
        color: #ffffff;
    } */
    .card-address {
        color: #000000;
        font-family: 'Arial';
        font-size: 12px;
        line-height: 19.5px;
        font-style: italic;
        text-transform: capitalize;
        margin-top: 10px;
    }
</style>
