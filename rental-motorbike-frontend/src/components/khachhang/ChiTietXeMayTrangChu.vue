<template>
    <div class="container-xl mt-4" style="font-family: Arial">
        <div class="row bg-white mb-3 p-2">
            <div class="route-link-detail">
                <a href="/trang-chu">Trang chủ</a> >>
                <router-link
                    :to="{
                        name: 'DanhSachXeMayTheoQuanView',
                        params: { maQuanHuyen: maQuanHuyens[chuCuaHang.maPhuongXa] }
                    }"
                    >{{ tenQuanHuyens[chuCuaHang.maPhuongXa] }}
                </router-link>
                >>
                <span>{{ xeMay.tenXe }}</span>
            </div>
        </div>

        <div class="row bg-white">
            <div class="col-7 p-4">
                <div class="row">
                    <img
                        v-if="anhXeMays.length > 0"
                        :src="anhXeMays[0].duongDan"
                        alt="Ảnh xe máy"
                        class="img-fit"
                    />
                </div>
                <div class="row mt-3 mb-3">
                    <div
                        id="carouselAnhXeMay"
                        class="carousel slide p-3 border border-light-subtle"
                        data-bs-ride="carousel"
                    >
                        <div class="carousel-inner">
                            <div
                                v-for="(group, index) in chunkedAnhXeMays"
                                :key="index"
                                class="carousel-item"
                                :class="{ active: index === 0 }"
                                data-bs-interval="3000"
                            >
                                <div class="d-flex justify-content-start">
                                    <img
                                        v-for="(item, subIndex) in group"
                                        :key="subIndex"
                                        :src="item.duongDan"
                                        class="img-thumbnail mx-1"
                                        alt="Ảnh xe máy hoặc cavet"
                                        style="width: 115px; height: 115px; object-fit: cover"
                                    />
                                </div>
                            </div>
                        </div>
                        <button
                            class="carousel-control-prev"
                            type="button"
                            data-bs-target="#carouselAnhXeMay"
                            data-bs-slide="prev"
                        >
                            <span
                                class="carousel-control-prev-icon prev-icon-detail"
                                aria-hidden="true"
                            ></span>
                            <span class="visually-hidden">Previous</span>
                        </button>
                        <button
                            class="carousel-control-next"
                            type="button"
                            data-bs-target="#carouselAnhXeMay"
                            data-bs-slide="next"
                        >
                            <span
                                class="carousel-control-next-icon next-icon-detail"
                                aria-hidden="true"
                            ></span>
                            <span class="visually-hidden">Next</span>
                        </button>
                    </div>
                </div>
            </div>
            <div class="col-5 p-4">
                <div class="row text-start">
                    <div class="text-uppercase fw-bold fs-2">{{ xeMay.tenXe }}</div>
                    <div class="mt-2 mb-2">{{ xeMay.loaiXe }} - {{ danhMucXe.tenDanhMucXe }}</div>
                    <div class="card-price fs-3 bg-silver p-4">
                        {{ formattedGiaThue(xeMay.giaThue) }}/Ngày
                    </div>
                    <div class="mt-3 mb-3">
                        Khu vực: {{ tenQuanHuyens[chuCuaHang.maPhuongXa] }} -
                        {{ tenPhuongXas[chuCuaHang.maPhuongXa] }}
                    </div>
                    <div>
                        <div>
                            <i class="fa-solid fa-circle-check"></i>
                            <span class="ms-3">02 mũ bảo hiểm 1/2 đầu</span>
                        </div>
                        <div>
                            <i class="fa-solid fa-circle-check"></i>
                            <span class="ms-3">02 áo mưa dùng một lần</span>
                        </div>
                    </div>
                </div>
                <div class="row mt-5">
                    <div class="col-6 text-center">
                        <button
                            type="button"
                            class="btn btn-primary btn-cart"
                            @click="themVaoGioHang(xeMay)"
                            title="Thêm vào giỏ hàng"
                        >
                            <i class="fa-solid fa-cart-plus"></i>
                            <span class="ms-2">Thêm vào giỏ hàng</span>
                        </button>
                    </div>
                    <div class="col-6 text-center">
                        <button
                            type="button"
                            class="btn btn-primary"
                            title="Đặt thuê xe"
                            @click.prevent="checkDangNhap"
                        >
                            Đặt thuê xe
                        </button>
                    </div>
                </div>
            </div>
        </div>
        <div class="row bg-white mt-4 p-4">
            <div class="col-9">
                <div class="row">
                    <div class="col-1">
                        <img :src="nguoiDung.anhDaiDien" alt="" width="60px" height="60px" />
                    </div>
                    <div class="col-5 align-content-center border-end">
                        <div class="detail-fullname">{{ nguoiDung.hoVaTen }}</div>
                        <div class="detail-address">
                            {{ nguoiDung.diaChi }}, {{ tenPhuongXas[chuCuaHang.maPhuongXa] }},
                            {{ tenQuanHuyens[chuCuaHang.maPhuongXa] }}, Đà Nẵng
                        </div>
                    </div>
                    <div class="col-6">
                        <div>
                            <span class="title-store">Cửa hàng: </span>
                            <span class="content-store">{{ chuCuaHang.tenCuaHang }}</span>
                        </div>
                        <div>
                            <span class="title-store">Địa chỉ cửa hàng: </span>
                            <span class="content-store">{{ chuCuaHang.diaChiCuaHang }}</span>
                        </div>
                        <div>
                            <span class="title-store">Số điện thoại cửa hàng: </span>
                            <span class="content-store">{{ chuCuaHang.soDienThoaiCuaHang }}</span>
                        </div>
                    </div>
                </div>
                <div class="row">
                    <div class="card-price fs-5 bg-silver p-3 text-uppercase">
                        Thông số kỹ thuật
                    </div>
                    <table class="width-table mt-3 table table-bordered border border-black">
                        <tr>
                            <th>Tên xe máy</th>
                            <td>{{ xeMay.tenXe }}</td>
                        </tr>
                        <tr>
                            <th>Biển số xe</th>
                            <td>{{ xeMay.bienSoXe }}</td>
                        </tr>
                        <tr>
                            <th>Màu xe</th>
                            <td>{{ xeMay.mauXe }}</td>
                        </tr>
                        <tr>
                            <th>Số khung</th>
                            <td>{{ xeMay.soKhung }}</td>
                        </tr>
                        <tr>
                            <th>Số máy</th>
                            <td>{{ xeMay.soMay }}</td>
                        </tr>
                        <tr>
                            <th>Loại xe</th>
                            <td>{{ xeMay.loaiXe }}</td>
                        </tr>
                        <tr>
                            <th>Hãng xe</th>
                            <td>{{ danhMucXe.tenDanhMucXe }}</td>
                        </tr>
                        <tr>
                            <th>Trạng thái hoạt động</th>
                            <td>{{ xeMay.trangThaiHoatDong }}</td>
                        </tr>
                    </table>
                    <div class="card-price fs-5 bg-silver p-3 text-uppercase">Chất lượng xe</div>
                    <template v-if="danhMucXe.maDanhMucXe === 'DMX001'">
                        <div class="mt-3">
                            <ul>
                                <li>
                                    Xe Honda có độ bền cực cao và được đánh giá là thương hiệu xe có
                                    độ bền tốt nhất tại Việt Nam. Hệ thống động cơ của xe Honda hoạt
                                    động ổn định trong nhiều năm mà ít gặp lỗi, phụ tùng dễ thay
                                    thế.
                                </li>
                                <li class="mt-2 mb-2">
                                    Tiết kiệm nhiên liệu vượt trội. Công nghệ PGM-FI (Phun xăng điện
                                    tử) giúp xe Honda có mức tiêu hao nhiên liệu rất thấp, trung
                                    bình từ 1.5 - 2.2L/100km tùy dòng xe.
                                </li>
                                <li>Chiếm khoảng 80% thị phần xe máy tại Việt Nam.</li>
                            </ul>
                        </div>
                    </template>
                    <template v-if="danhMucXe.maDanhMucXe === 'DMX002'">
                        <div class="mt-3">
                            <ul>
                                <li>
                                    Thiết kế thể thao, mạnh mẽ. Xe Yamaha có kiểu dáng cá tính, sắc
                                    nét, hướng đến giới trẻ. Các dòng xe như Exciter, NVX có thiết
                                    kế thể thao, phù hợp với người thích sự năng động.
                                </li>
                                <li class="mt-2 mb-2">
                                    Động cơ mạnh, cảm giác lái tốt. Xe Yamaha có khả năng tăng tốc
                                    tốt, mang lại trải nghiệm lái phấn khích hơn so với Honda.
                                </li>
                                <li>
                                    Công nghệ hiện đại. Blue Core: Giúp xe tiết kiệm nhiên liệu tốt
                                    hơn so với các dòng Yamaha đời cũ. Smart Key: Hệ thống khóa
                                    thông minh có trên các dòng xe ga cao cấp.
                                </li>
                            </ul>
                        </div>
                    </template>
                    <template v-if="danhMucXe.maDanhMucXe === 'DMX003'">
                        <div class="mt-3">
                            <ul>
                                <li>
                                    Động cơ bền bỉ, mạnh mẽ. Xe Suzuki có hiệu suất động cơ cao, khả
                                    năng tăng tốc tốt, vận hành ổn định trên đường dài.
                                </li>
                                <li class="mt-2 mb-2">
                                    Thiết kế thể thao, cá tính. Các mẫu xe côn tay của Suzuki như
                                    Raider 150, GSX-S150 có thiết kế táo bạo, mạnh mẽ, phù hợp với
                                    dân chơi xe.
                                </li>
                                <li>
                                    Độ bền tốt. Chất lượng động cơ và linh kiện của Suzuki khá tốt,
                                    ít hỏng vặt, giúp xe có tuổi thọ dài nếu được bảo dưỡng đúng
                                    cách.
                                </li>
                            </ul>
                        </div>
                    </template>
                    <template v-if="danhMucXe.maDanhMucXe === 'DMX004'">
                        <div class="mt-3">
                            <ul>
                                <li>
                                    Giá rẻ, phù hợp túi tiền. SYM tập trung vào phân khúc xe giá
                                    thấp, phù hợp với người có thu nhập trung bình.
                                </li>
                                <li class="mt-2 mb-2">
                                    Dung tích cốp rộng. Các dòng xe tay ga như Attila, Fancy có cốp
                                    rất rộng, tiện lợi cho việc đựng đồ.
                                </li>
                                <li>
                                    Phù hợp với nhu cầu cơ bản. Xe SYM đáp ứng tốt nhu cầu di chuyển
                                    đơn giản với mức đầu tư thấp.
                                </li>
                            </ul>
                        </div>
                    </template>
                </div>
            </div>
            <div class="col-3">
                <div
                    v-for="item in xeMays.slice(0, 3)"
                    :key="(item as any).xeMay.maXeMay"
                    class="mb-4"
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
                                height="200px"
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

        <div class="row bg-white p-3 mt-3">
            <div class="text-uppercase card-price fw-bolder fs-5 mt-2 mb-2 pb-0 ps-2">
                <span>Xe máy khác</span>
            </div>
            <div
                v-for="item in xeMayKhacs.slice(0, visibleCount)"
                :key="(item as any).xeMay.maXeMay"
                class="col-12 col-sm-6 col-md-4 col-lg-3 mb-4"
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
                            height="200px"
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
            <div class="text-center" v-if="xeMayKhacs.length > visibleCount">
                <button class="btn btn-primary" @click="showMore">Xem thêm</button>
            </div>
        </div>
    </div>
    <PopupLoading :isLoading="isLoading" />
</template>

<script lang="ts">
    import {
        getPhuongXaByMaPhuongXa,
        getQuanHuyenByMaPhuongXa,
        getQuanHuyens
    } from '@/services/authService'
    import { getMaNguoiDung } from '@/services/localStorageService'
    import { getXeMayByMaXeMay, getXeMays, getXeMaysByChuCuaHang } from '@/services/xeMayService'
    import { computed, defineComponent, inject, nextTick, onMounted, ref, watch } from 'vue'
    import { useRoute, useRouter } from 'vue-router'
    import PopupLoading from '../dungchung/PopupLoading.vue'

    export default defineComponent({
        name: 'ChiTietXeMayTrangChu',
        components: {
            PopupLoading
        },
        setup() {
            const visibleRows = ref(4)
            const itemsPerRow = 4
            const visibleCount = computed(() => visibleRows.value * itemsPerRow)
            const route = useRoute()
            const router = useRouter()
            const xeMay = ref<Record<string, string>>({})
            const danhMucXe = ref<Record<string, string>>({})
            const chuCuaHang = ref<Record<string, string>>({})
            const nguoiDung = ref<Record<string, string>>({})
            const xeMays = ref<Record<string, string>[]>([])
            const xeMayKhacs = ref<Record<string, string>[]>([])
            const quanHuyens = ref<Record<string, unknown>[]>([])
            const maXeMay = ref(String(route.params.maXeMay))
            const maQuanHuyens = ref<Record<string, string>>({})
            const tenQuanHuyens = ref<Record<string, string>>({})
            const tenPhuongXas = ref<Record<string, string>>({})
            const anhXeMays = ref<Record<string, string>[]>([])
            const isLoggedIn = inject('isLoggedIn', ref(false))
            const isLoading = ref(true)

            const checkDangNhap = () => {
                if (!isLoggedIn.value) {
                    alert('Vui lòng đăng nhập để đặt xe!')
                } else {
                    router.push({ name: 'ThueXeMayView', params: { maXeMay: xeMay.value.maXeMay } })
                }
            }

            const showMore = () => {
                visibleRows.value += 4
            }

            const formattedGiaThue = (giaThue: string) => {
                if (!giaThue) return '0'
                const numberGiaThue = Number(giaThue)
                return numberGiaThue.toLocaleString('vi-VN')
            }

            const fetchQuanHuyens = async () => {
                const response = await getQuanHuyens()
                quanHuyens.value = response
            }

            const fetchXeMay = async () => {
                const response = await getXeMayByMaXeMay(String(route.params.maXeMay))
                xeMay.value = response.xeMay
                danhMucXe.value = response.danhMucXe
                chuCuaHang.value = response.chuCuaHang
                nguoiDung.value = response.nguoiDung
                anhXeMays.value = response.anhXeMays

                const [phuongXa, quanHuyen] = await Promise.all([
                    getTenPhuongXa(String(response?.chuCuaHang?.maPhuongXa)),
                    getTenQuanHuyen(String(response?.chuCuaHang?.maPhuongXa)),
                    getMaQuanHuyen(String(response?.chuCuaHang?.maPhuongXa))
                ])

                tenPhuongXas.value[response.chuCuaHang?.maPhuongXa] = phuongXa
                tenQuanHuyens.value[response.chuCuaHang?.maPhuongXa] = quanHuyen
            }

            const chunkedAnhXeMays = computed(() => {
                const chunkSize = 5
                return anhXeMays.value.reduce<Record<string, string>[][]>((result, item, index) => {
                    const chunkIndex = Math.floor(index / chunkSize)
                    if (!result[chunkIndex]) result[chunkIndex] = []
                    result[chunkIndex].push(item)
                    return result
                }, [])
            })

            const fetchXeMaysByChuCuaHang = async () => {
                const response = await getXeMaysByChuCuaHang(chuCuaHang.value.maChuCuaHang)
                const maPhuongXaList = response.map(
                    (item: { chuCuaHang: { maPhuongXa: string } }) => item.chuCuaHang?.maPhuongXa
                )

                const [tenPhuongXaResults, tenQuanHuyenResults] = await Promise.all([
                    Promise.all(maPhuongXaList.map(getTenPhuongXa)),
                    Promise.all(maPhuongXaList.map(getTenQuanHuyen))
                ])

                xeMays.value = response
                    .filter(
                        (item: { xeMay: { maXeMay: string } }) =>
                            item.xeMay.maXeMay !== xeMay.value.maXeMay
                    )
                    .map((item: object, index: number) => ({
                        ...item,
                        tenPhuongXa: tenPhuongXaResults[index],
                        tenQuanHuyen: tenQuanHuyenResults[index]
                    }))
            }

            const fetchXeMayKhacs = async () => {
                const response = await getXeMays()
                const maPhuongXaList = response.map(
                    (item: { chuCuaHang: { maPhuongXa: string } }) => item.chuCuaHang?.maPhuongXa
                )

                const [tenPhuongXaResults, tenQuanHuyenResults] = await Promise.all([
                    Promise.all(maPhuongXaList.map(getTenPhuongXa)),
                    Promise.all(maPhuongXaList.map(getTenQuanHuyen))
                ])

                xeMayKhacs.value = response
                    .filter(
                        (item: { xeMay: { maXeMay: string } }) =>
                            item.xeMay.maXeMay !== xeMay.value.maXeMay
                    )
                    .map((item: object, index: number) => ({
                        ...item,
                        tenPhuongXa: tenPhuongXaResults[index],
                        tenQuanHuyen: tenQuanHuyenResults[index]
                    }))
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

            const getMaQuanHuyen = async (maPhuongXa: string) => {
                if (!maQuanHuyens.value[maPhuongXa]) {
                    const quanHuyen = await getQuanHuyenByMaPhuongXa(maPhuongXa)
                    maQuanHuyens.value[maPhuongXa] = quanHuyen
                        ? quanHuyen.maQuanHuyen
                        : 'Không xác định'
                }
                return maQuanHuyens.value[maPhuongXa]
            }

            watch(
                () => route.params.maXeMay,
                async (newMaXeMay, oldMaXeMay) => {
                    if (newMaXeMay !== oldMaXeMay) {
                        maXeMay.value = String(newMaXeMay)

                        xeMay.value = {}
                        xeMays.value = []
                        xeMayKhacs.value = []

                        await nextTick()
                        await fetchXeMay()
                        await fetchXeMaysByChuCuaHang()
                        await fetchXeMayKhacs()
                    }
                }
            )

            const themVaoGioHang = (xeMay: Record<string, string>) => {
                if (!isLoggedIn.value) {
                    alert('Vui lòng đăng nhập để thêm vào giỏ hàng!')
                    return
                }

                const item = {
                    maXeMay: xeMay.maXeMay,
                    tenXe: xeMay.tenXe,
                    giaThue: xeMay.giaThue,
                    loaiXe: xeMay.loaiXe,
                    danhMucXe: danhMucXe.value.tenDanhMucXe,
                    mauXe: xeMay.mauXe,
                    bienSoXe: xeMay.bienSoXe,
                    soMay: xeMay.soMay,
                    soKhung: xeMay.soKhung,
                    anhXeMay: anhXeMays.value[0].duongDan,
                    maChuCuaHang: chuCuaHang.value.maChuCuaHang
                }

                const cartKey = `cart_user_${getMaNguoiDung()}`

                const storedCart = localStorage.getItem(cartKey)
                const cart = storedCart ? JSON.parse(storedCart) : []

                const exists = cart.some((x: Record<string, string>) => x.maXeMay === item.maXeMay)
                if (exists) {
                    alert(`Xe "${item.tenXe}" đã có trong giỏ hàng.`)
                    return
                }

                const isSameChuCuaHang = cart.some(
                    (x: Record<string, string>) => x.maChuCuaHang !== item.maChuCuaHang
                )

                if (isSameChuCuaHang) {
                    alert('Không thể thêm xe từ cửa hàng khác vào giỏ hàng.')
                    return
                }

                cart.push(item)
                localStorage.setItem(cartKey, JSON.stringify(cart))
                alert(`Đã thêm xe "${item.tenXe}" vào giỏ hàng.`)
            }

            onMounted(async () => {
                await Promise.all([fetchQuanHuyens(), fetchXeMay()])
                isLoading.value = false
                await fetchXeMaysByChuCuaHang()
                await fetchXeMayKhacs()
            })

            return {
                isLoading,
                visibleCount,
                showMore,
                xeMayKhacs,
                maXeMay,
                quanHuyens,
                xeMay,
                danhMucXe,
                chuCuaHang,
                nguoiDung,
                formattedGiaThue,
                tenPhuongXas,
                maQuanHuyens,
                tenQuanHuyens,
                xeMays,
                anhXeMays,
                chunkedAnhXeMays,
                checkDangNhap,
                themVaoGioHang
            }
        }
    })
</script>

<style>
    .route-link-detail > span {
        color: #23689b;
        font-size: 14px;
        font-weight: 600;
    }
    .route-link-detail > a {
        color: #000000;
        font-size: 14px;
        font-weight: 600;
    }
    .route-link-detail > a:hover {
        color: #ff8435;
    }

    .card-address {
        color: #000000;
        font-family: 'Arial';
        font-size: 12px;
        line-height: 19.5px;
        font-style: italic;
        text-transform: capitalize;
        margin-top: 10px;
    }
    .prev-icon-detail {
        margin-left: -70px !important;
        padding: 35% 10px !important;
    }
    .next-icon-detail {
        margin-right: -70px !important;
        padding: 35% 10px !important;
    }

    .img-fit {
        width: 100%;
        height: auto;
        max-height: 250px;
        object-fit: contain;
        border-radius: 8px;
        display: block;
        margin: 0 auto;
    }
    .bg-silver {
        background-color: #f5f5f5;
    }
    .btn-cart {
        background-color: #ffffff;
        border: 1px solid #ff8435;
        color: #ff8435;
    }
    .detail-fullname {
        color: #000000;
        font-weight: 700;
        font-size: 16px;
    }
    .detail-address {
        color: #000000;
        opacity: 0.5;
        font-size: 14px;
        font-style: italic;
    }
    .title-store {
        font-size: 15px;
        color: #000000;
        opacity: 0.8;
    }
    .content-store {
        font-size: 17px;
        color: #000000;
        font-weight: 700;
        text-transform: capitalize;
    }

    .width-table > tr > th {
        width: 30%;
        font-size: 16px;
        text-transform: uppercase;
        border-right: #000000 1px solid;
        font-weight: 700;
        padding: 10px 0px 10px 20px;
    }
    .width-table > tr > td {
        width: 70%;
        font-size: 16px;
        padding: 10px 0px 10px 30px;
    }
</style>
