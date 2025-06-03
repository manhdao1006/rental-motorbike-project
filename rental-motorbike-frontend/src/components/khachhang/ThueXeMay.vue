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
                <router-link
                    :to="{ name: 'ChiTietXeMayTrangChuView', params: { maXeMay: xeMay.maXeMay } }"
                    >{{ xeMay.tenXe }}</router-link
                >
                >>
                <span>Thuê xe</span>
            </div>
        </div>

        <div class="row bg-white p-3">
            <div class="row">
                <div class="text-uppercase card-price fw-bolder fs-5 mt-3 mb-0 pb-0 ps-2">
                    <span>Thông tin đơn hàng</span>
                </div>
                <div v-if="isError" class="alert alert-danger">
                    {{ messageError }}
                </div>
                <div class="col-7">
                    <div class="row">
                        <div class="col-4 p-4">
                            <div class="row">
                                <img
                                    v-if="anhXeMays.length > 0"
                                    :src="anhXeMays[0].duongDan"
                                    alt="Ảnh xe máy"
                                    class="img-fit"
                                />
                            </div>
                        </div>
                        <div class="col-8 p-4">
                            <div class="row text-start">
                                <div class="text-uppercase fw-bold fs-5">{{ xeMay.tenXe }}</div>
                                <div class="mt-2">
                                    {{ xeMay.loaiXe }} - {{ danhMucXe.tenDanhMucXe }}
                                </div>
                                <div class="card-price fs-5 bg-silver p-3">
                                    Giá thuê: {{ formattedGiaThue(xeMay.giaThue) }}/Ngày
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="col-5 p-4">
                    <div>
                        <span class="title-store">Quản lý cửa hàng: </span>
                        <span class="content-store">{{ nguoiDungChuCuaHang.hoVaTen }}</span>
                    </div>
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
                <div class="col-6">
                    <div class="row">
                        <div class="col-6">
                            <div class="mb-3">
                                <label for="tuNgay" class="form-label"
                                    >Thuê từ ngày<span class="text-danger">*</span></label
                                >
                                <input
                                    v-model="chiTietDonHang.tuNgay"
                                    type="date"
                                    class="form-control"
                                    id="tuNgay"
                                    :min="minDate"
                                    :max="chiTietDonHang.denNgay"
                                />
                            </div>
                        </div>
                        <div class="col-6">
                            <div class="mb-3">
                                <label for="denNgay" class="form-label"
                                    >Thuê đến ngày<span class="text-danger">*</span></label
                                >
                                <input
                                    v-model="chiTietDonHang.denNgay"
                                    type="date"
                                    class="form-control"
                                    id="denNgay"
                                    :min="minDenNgay"
                                />
                            </div>
                        </div>
                    </div>
                    <div class="mb-3">
                        <label for="phuongThucNhanXe" class="form-label"
                            >Phương thức nhận xe<span class="text-danger">*</span></label
                        >
                        <select
                            v-model="donHang.phuongThucNhanXe"
                            class="form-select"
                            aria-label="Default select example"
                        >
                            <option selected disabled>Chọn phương thức nhận xe</option>
                            <option value="Nhận tại cửa hàng">Nhận tại cửa hàng</option>
                            <option value="Giao xe tận nơi">Giao xe tận nơi</option>
                        </select>
                    </div>
                    <div v-if="donHang.phuongThucNhanXe === 'Giao xe tận nơi'" class="mb-3">
                        <div class="form-check">
                            <input
                                class="form-check-input"
                                type="radio"
                                id="giaoToi"
                                value="SELF"
                                v-model="donHang.kieuGiaoXe"
                            />
                            <label class="form-check-label" for="giaoToi">
                                Giao đến địa chỉ của tôi
                            </label>
                        </div>
                        <div class="form-check">
                            <input
                                class="form-check-input"
                                type="radio"
                                id="giaoKhac"
                                value="OTHER"
                                v-model="donHang.kieuGiaoXe"
                            />
                            <label class="form-check-label" for="giaoKhac">
                                Giao đến địa chỉ khác
                            </label>
                        </div>
                    </div>
                </div>
                <div class="col-6">
                    <div class="mb-3">
                        <label for="phuongThucThanhToan" class="form-label"
                            >Phương thức thanh toán<span class="text-danger">*</span></label
                        >
                        <select
                            v-model="donHang.phuongThucThanhToan"
                            class="form-select"
                            aria-label="Default select example"
                        >
                            <option selected disabled>Chọn phương thức thanh toán</option>
                            <option value="Thanh toán bằng tiền mặt">
                                Thanh toán bằng tiền mặt
                            </option>
                            <option value="Thanh toán qua ngân hàng">
                                Thanh toán qua ngân hàng
                            </option>
                        </select>
                    </div>
                    <div class="mb-3">
                        <label for="lyDoThueXe" class="form-label"
                            >Lý do thuê xe<span class="text-danger">*</span></label
                        >
                        <textarea
                            v-model="donHang.lyDoThueXe"
                            type="text"
                            class="form-control"
                            id="lyDoThueXe"
                            rows="4"
                        ></textarea>
                    </div>
                </div>
                <div class="mb-3" v-if="donHang.kieuGiaoXe === 'SELF'">
                    <label for="diaChi" class="form-label"
                        >Địa chỉ của tôi<span class="text-danger">*</span></label
                    >
                    <textarea
                        v-model="nguoiDungKhachHang.diaChi"
                        type="text"
                        class="form-control"
                        id="diaChi"
                        rows="1"
                    ></textarea>
                </div>
                <div class="mb-3" v-if="donHang.kieuGiaoXe === 'OTHER'">
                    <label for="diaChiGiaoXe" class="form-label"
                        >Địa chỉ khác<span class="text-danger">*</span></label
                    >
                    <textarea
                        v-model="donHang.diaChiGiaoXe"
                        type="text"
                        class="form-control"
                        id="diaChiGiaoXe"
                        rows="1"
                    ></textarea>
                </div>
            </div>
            <div
                v-if="chiTietDonHang.tuNgay && chiTietDonHang.denNgay"
                class="card-price fs-5 bg-silver p-3"
            >
                Tổng tiền: {{ formattedTongTien(tongTien) }} VNĐ
            </div>
        </div>
        <div class="row bg-white mt-4 p-3">
            <div class="text-uppercase card-price fw-bolder fs-5 mt-2 mb-2 pb-0 ps-2">
                <span>Thông tin khách hàng</span>
            </div>
            <div class="row">
                <div class="col-6">
                    <div class="mb-3">
                        <label for="hoVaTen" class="form-label"
                            >Họ và tên<span class="text-danger">*</span></label
                        >
                        <input
                            v-model="nguoiDungKhachHang.hoVaTen"
                            type="text"
                            class="form-control"
                            id="hoVaTen"
                        />
                    </div>
                    <div class="mb-3">
                        <label for="email" class="form-label"
                            >Email<span class="text-danger">*</span></label
                        >
                        <input
                            v-model="nguoiDungKhachHang.email"
                            type="email"
                            class="form-control"
                            id="email"
                        />
                        <div v-if="isErrorEmail" class="text-danger">
                            {{ messageEmail }}
                        </div>
                    </div>
                </div>
                <div class="col-6">
                    <div class="mb-3">
                        <label for="ngaySinh" class="form-label"
                            >Ngày sinh<span class="text-danger">*</span></label
                        >
                        <input
                            v-model="nguoiDungKhachHang.ngaySinh"
                            type="date"
                            class="form-control"
                            id="ngaySinh"
                        />
                    </div>
                    <div class="mb-3">
                        <label for="gioiTinh" class="form-label"
                            >Giới tính<span class="text-danger">*</span></label
                        >
                        <select
                            v-model="nguoiDungKhachHang.gioiTinh"
                            class="form-select"
                            aria-label="Default select example"
                        >
                            <option selected disabled>Chọn giới tính</option>
                            <option value="Nam">Nam</option>
                            <option value="Nữ">Nữ</option>
                            <option value="Khác">Khác</option>
                        </select>
                    </div>
                </div>
            </div>
            <div class="row">
                <div class="col-4">
                    <div class="mb-3">
                        <label for="soDienThoai" class="form-label"
                            >Số điện thoại<span class="text-danger">*</span></label
                        >
                        <input
                            v-model="nguoiDungKhachHang.soDienThoai"
                            type="text"
                            class="form-control"
                            id="soDienThoai"
                        />
                        <div v-if="isErrorSoDienThoai" class="text-danger">
                            {{ messageSoDienThoai }}
                        </div>
                    </div>
                </div>
                <div class="col-4">
                    <div class="mb-3">
                        <label for="soGPLX" class="form-label"
                            >Số giấy phép lái xe<span class="text-danger">*</span></label
                        >
                        <input
                            v-model="khachHang.soGPLX"
                            type="text"
                            class="form-control"
                            id="soGPLX"
                        />
                        <div v-if="isErrorSoGPLX" class="text-danger">
                            {{ messageSoGPLX }}
                        </div>
                    </div>
                </div>
                <div class="col-4">
                    <div class="mb-3">
                        <label for="soCCCD" class="form-label"
                            >Số căn cước công dân<span class="text-danger">*</span></label
                        >
                        <input
                            v-model="nguoiDungKhachHang.soCCCD"
                            type="text"
                            class="form-control"
                            id="soCCCD"
                        />
                        <div v-if="isErrorSoCCCD" class="text-danger">
                            {{ messageSoCCCD }}
                        </div>
                    </div>
                </div>
            </div>
            <div class="text-center mt-2">
                <button
                    type="button"
                    class="btn btn-success"
                    title="Thuê xe"
                    @click.prevent="handleThueXe"
                    :disabled="isLoading"
                >
                    Thuê xe
                </button>
            </div>
        </div>

        <div class="row bg-white p-3 mt-3">
            <div class="text-uppercase card-price fw-bolder fs-5 mt-2 mb-2 pb-0 ps-2">
                <span>Xe máy khác</span>
            </div>
            <div
                v-for="item in xeMays.slice(0, visibleCount)"
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
            <div class="text-center" v-if="xeMays.length > visibleCount">
                <button class="btn btn-primary" @click="showMore">Xem thêm</button>
            </div>
        </div>
    </div>
    <PopupLoading :isLoading="isLoading" />
</template>

<script lang="ts">
    import { useDate } from '@/composables/useDate'
    import {
        getPhuongXaByMaPhuongXa,
        getQuanHuyenByMaPhuongXa,
        uploadWord
    } from '@/services/authService'
    import { addChiTietDonHang } from '@/services/chiTietDonHangService'
    import { addDonHang } from '@/services/donHangService'
    import { getKhachHangByMaKhachHang, updateKhachHang } from '@/services/khachHangService'
    import { getMaNguoiDung } from '@/services/localStorageService'
    import { getXeMayByMaXeMay, getXeMays, updateXeMay } from '@/services/xeMayService'
    import {
        validateEmail,
        validateSoCCCD,
        validateSoDienThoai,
        validateSoGPLX
    } from '@/utils/validation'
    import { computed, defineComponent, onMounted, ref, watch } from 'vue'
    import { useRoute, useRouter } from 'vue-router'
    import PopupLoading from '../dungchung/PopupLoading.vue'

    export default defineComponent({
        name: 'ThueXeMay',
        components: {
            PopupLoading
        },
        setup() {
            const maQuanHuyens = ref<Record<string, string>>({})
            const visibleRows = ref(4)
            const itemsPerRow = 4
            const visibleCount = computed(() => visibleRows.value * itemsPerRow)
            const xeMays = ref<Record<string, string>[]>([])
            const tenQuanHuyens = ref<Record<string, string>>({})
            const tenPhuongXas = ref<Record<string, string>>({})
            const route = useRoute()
            const router = useRouter()
            const xeMay = ref<Record<string, string>>({})
            const danhMucXe = ref<Record<string, string>>({})
            const chuCuaHang = ref<Record<string, string>>({})
            const nguoiDungChuCuaHang = ref<Record<string, string>>({})
            const khachHang = ref<Record<string, string>>({})
            const nguoiDungKhachHang = ref<Record<string, string>>({})
            const donHang = ref<Record<string, string>>({})
            const chiTietDonHang = ref<Record<string, string>>({})
            const maXeMay = ref(String(route.params.maXeMay))
            const anhXeMays = ref<Record<string, string>[]>([])
            const isError = ref(false)
            const messageError = ref<string>('')
            const isErrorEmail = ref(false)
            const messageEmail = ref<string>('')
            const isErrorSoDienThoai = ref(false)
            const messageSoDienThoai = ref<string>('')
            const isErrorSoCCCD = ref(false)
            const messageSoCCCD = ref<string>('')
            const isErrorSoGPLX = ref(false)
            const messageSoGPLX = ref<string>('')
            const isLoading = ref(false)
            const minDate = new Date().toISOString().split('T')[0]
            const minDenNgay = computed(() => {
                return chiTietDonHang.value.tuNgay || minDate
            })

            const formattedTongTien = (gia: number) => {
                return new Intl.NumberFormat('vi-VN').format(gia)
            }

            const formattedGiaThue = (giaThue: string) => {
                if (!giaThue) return '0'
                const numberGiaThue = Number(giaThue)
                return numberGiaThue.toLocaleString('vi-VN')
            }

            const showMore = () => {
                visibleRows.value += 4
            }

            watch(
                () => donHang.value.phuongThucNhanXe,
                (newValue) => {
                    if (newValue === 'Giao xe tận nơi') {
                        donHang.value.kieuGiaoXe = 'SELF'
                    } else {
                        donHang.value.kieuGiaoXe = ''
                    }
                }
            )

            const fetchXeMay = async () => {
                const response = await getXeMayByMaXeMay(String(route.params.maXeMay))
                xeMay.value = response.xeMay
                danhMucXe.value = response.danhMucXe
                chuCuaHang.value = response.chuCuaHang
                nguoiDungChuCuaHang.value = response.nguoiDung
                anhXeMays.value = response.anhXeMays
            }

            const fetchKhachHang = async () => {
                const response = await getKhachHangByMaKhachHang(getMaNguoiDung())
                khachHang.value = response.khachHang
                nguoiDungKhachHang.value = response.nguoiDung
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

            const fetchXeMays = async () => {
                const response = await getXeMays()
                const maPhuongXaList = response.map(
                    (item: { chuCuaHang: { maPhuongXa: string } }) => item.chuCuaHang?.maPhuongXa
                )

                const [tenPhuongXaResults, tenQuanHuyenResults, maQuanHuyenResults] =
                    await Promise.all([
                        Promise.all(maPhuongXaList.map(getTenPhuongXa)),
                        Promise.all(maPhuongXaList.map(getTenQuanHuyen)),
                        Promise.all(maPhuongXaList.map(getMaQuanHuyen))
                    ])

                xeMays.value = response
                    .filter(
                        (item: { xeMay: { maXeMay: string } }) =>
                            item.xeMay.maXeMay !== xeMay.value.maXeMay
                    )
                    .map((item: object, index: number) => ({
                        ...item,
                        tenPhuongXa: tenPhuongXaResults[index],
                        tenQuanHuyen: tenQuanHuyenResults[index],
                        maQuanHuyen: maQuanHuyenResults[index]
                    }))
            }

            onMounted(async () => {
                await Promise.all([fetchXeMay(), fetchKhachHang(), fetchXeMays()])
            })

            const tongTien = computed(() => {
                const giaThue = xeMay.value.giaThue as unknown as number
                const tuNgay = chiTietDonHang.value.tuNgay
                const denNgay = chiTietDonHang.value.denNgay

                const validTuNgay = tuNgay && typeof tuNgay === 'string'
                const validDenNgay = denNgay && typeof denNgay === 'string'

                const tuNgayDate = validTuNgay ? new Date(tuNgay) : new Date()
                const denNgayDate = validDenNgay ? new Date(denNgay) : new Date()

                const soNgayThue =
                    validTuNgay && validDenNgay
                        ? (new Date(denNgayDate).getTime() - new Date(tuNgayDate).getTime()) /
                          (1000 * 3600 * 24)
                        : 0

                return soNgayThue === 0 ? giaThue : soNgayThue * giaThue
            })

            const handleThueXe = async () => {
                let hasError = false

                if (
                    !nguoiDungKhachHang.value.hoVaTen ||
                    !nguoiDungKhachHang.value.email ||
                    !nguoiDungKhachHang.value.ngaySinh ||
                    !nguoiDungKhachHang.value.gioiTinh ||
                    !nguoiDungKhachHang.value.soDienThoai ||
                    !nguoiDungKhachHang.value.soCCCD ||
                    !khachHang.value.soGPLX ||
                    !donHang.value.phuongThucThanhToan ||
                    !donHang.value.phuongThucNhanXe ||
                    !donHang.value.lyDoThueXe ||
                    !chiTietDonHang.value.tuNgay ||
                    !chiTietDonHang.value.denNgay
                ) {
                    isError.value = true
                    messageError.value = 'Vui lòng nhập đầy đủ các trường dữ liệu!'
                    setTimeout(() => {
                        isError.value = false
                        messageError.value = ''
                    }, 3000)
                    return
                }

                if (
                    (donHang.value.kieuGiaoXe === 'SELF' && !nguoiDungKhachHang.value.diaChi) ||
                    (donHang.value.kieuGiaoXe === 'OTHER' && !donHang.value.diaChiGiaoXe)
                ) {
                    isError.value = true
                    messageError.value = 'Vui lòng nhập đầy đủ các trường dữ liệu!'
                    setTimeout(() => {
                        isError.value = false
                        messageError.value = ''
                    }, 3000)
                    return
                }

                const soCCCDCheck = validateSoCCCD(String(nguoiDungKhachHang.value.soCCCD))
                if (!soCCCDCheck.isValid) {
                    isErrorSoCCCD.value = true
                    messageSoCCCD.value = soCCCDCheck.message || ''
                    hasError = true
                }

                const soDienThoaiCheck = validateSoDienThoai(
                    String(nguoiDungKhachHang.value.soDienThoai)
                )
                if (!soDienThoaiCheck.isValid) {
                    isErrorSoDienThoai.value = true
                    messageSoDienThoai.value = soDienThoaiCheck.message || ''
                    hasError = true
                }

                const soGPLXCheck = validateSoGPLX(String(khachHang.value.soGPLX))
                if (!soGPLXCheck.isValid) {
                    isErrorSoGPLX.value = true
                    messageSoGPLX.value = soGPLXCheck.message || ''
                    hasError = true
                }

                const emailCheck = validateEmail(String(nguoiDungKhachHang.value.email))
                if (!emailCheck.isValid) {
                    isErrorEmail.value = true
                    messageEmail.value = emailCheck.message || ''
                    hasError = true
                }

                if (hasError) {
                    setTimeout(() => {
                        isErrorSoDienThoai.value = false
                        messageSoDienThoai.value = ''
                        isErrorEmail.value = false
                        messageEmail.value = ''
                        isErrorSoGPLX.value = false
                        messageSoGPLX.value = ''
                        isErrorSoCCCD.value = false
                        messageSoCCCD.value = ''
                    }, 3000)
                    return
                }

                isLoading.value = true
                try {
                    const formData = new FormData()
                    Object.entries(nguoiDungKhachHang.value).forEach(([key, value]) => {
                        if (
                            key !== 'trangThaiXoa' &&
                            key !== 'vaiTros' &&
                            key !== 'matKhau' &&
                            key !== 'maNguoiDung' &&
                            key !== 'maChuCuaHang' &&
                            value !== undefined
                        ) {
                            formData.append(key, value || '')
                        }
                    })
                    Object.entries(khachHang.value).forEach(([key, value]) => {
                        if (
                            key !== 'hopDongs' &&
                            key !== 'donHangs' &&
                            key !== 'trangThaiXoa' &&
                            key !== 'maNguoiDung' &&
                            value !== undefined
                        ) {
                            formData.append(key, value || '')
                        }
                    })

                    const responseKhachHang = await updateKhachHang(
                        String(nguoiDungKhachHang.value.maNguoiDung),
                        formData
                    )
                    if (responseKhachHang.success) {
                        const [phuongXaChu, quanHuyenChu] = await Promise.all([
                            getPhuongXaByMaPhuongXa(chuCuaHang.value.maPhuongXa),
                            getQuanHuyenByMaPhuongXa(chuCuaHang.value.maPhuongXa)
                        ])

                        const tenPhuongXa = phuongXaChu.tenPhuongXa
                        const tenQuanHuyen = quanHuyenChu.tenQuanHuyen

                        const wordFormData: Record<string, unknown> = {
                            tenChuCuaHang: nguoiDungChuCuaHang.value.hoVaTen,
                            ngaySinhChuCuaHang: useDate(nguoiDungChuCuaHang.value.ngaySinh),
                            soCCCDChuCuaHang: nguoiDungChuCuaHang.value.soCCCD,
                            diaChiChuCuaHang: nguoiDungChuCuaHang.value.diaChi,
                            phuongXaChuCuaHang: tenPhuongXa,
                            quanHuyenChuCuaHang: tenQuanHuyen,
                            soDienThoaiChuCuaHang: nguoiDungChuCuaHang.value.soDienThoai,
                            tenCuaHang: chuCuaHang.value.tenCuaHang,
                            diaChiCuaHang: chuCuaHang.value.diaChiCuaHang,
                            soDienThoaiCuaHang: chuCuaHang.value.soDienThoaiCuaHang,
                            tenKhachHang: nguoiDungKhachHang.value.hoVaTen,
                            ngaySinhKhachHang: useDate(nguoiDungKhachHang.value.ngaySinh),
                            soCCCDKhachHang: nguoiDungKhachHang.value.soCCCD,
                            diaChiKhachHang: nguoiDungKhachHang.value.diaChi,
                            soDienThoaiKhachHang: nguoiDungKhachHang.value.soDienThoai,
                            danhSachXe: [
                                {
                                    tenXe: xeMay.value.tenXe,
                                    loaiXe: xeMay.value.loaiXe,
                                    danhMucXe: danhMucXe.value.tenDanhMucXe,
                                    mauXe: xeMay.value.mauXe,
                                    bienSoXe: xeMay.value.bienSoXe,
                                    soMay: xeMay.value.soMay,
                                    soKhung: xeMay.value.soKhung,
                                    giaThue: formattedGiaThue(xeMay.value.giaThue),
                                    tuNgay: useDate(chiTietDonHang.value.tuNgay),
                                    denNgay: useDate(chiTietDonHang.value.denNgay)
                                }
                            ],
                            lyDoThueXe: donHang.value.lyDoThueXe,
                            phuongThucThanhToan: donHang.value.phuongThucThanhToan,
                            tongGiaThue: formattedTongTien(tongTien.value)
                        }
                        const responseUploadWord = await uploadWord(wordFormData)

                        if (responseUploadWord) {
                            donHang.value.maKhachHang = khachHang.value.maKhachHang
                            donHang.value.maNhanVien = ''
                            donHang.value.tepDinhKem = responseUploadWord

                            const formData = new FormData()
                            Object.entries(donHang.value).forEach(([key, value]) => {
                                formData.append(key, value || '')
                            })
                            const responseDonHang = await addDonHang(formData)

                            if (responseDonHang.success) {
                                chiTietDonHang.value.maDonHang = responseDonHang.result.maDonHang
                                chiTietDonHang.value.maXeMay = xeMay.value.maXeMay
                                chiTietDonHang.value.giaThue = xeMay.value.giaThue

                                const formData = new FormData()
                                Object.entries(chiTietDonHang.value).forEach(([key, value]) => {
                                    formData.append(key, value || '')
                                })
                                const responseChiTietDonHang = await addChiTietDonHang(formData)

                                if (responseChiTietDonHang.success) {
                                    const formatDate = (date: string | Date) =>
                                        new Date(date).toISOString().slice(0, 10)

                                    const today = formatDate(new Date())
                                    const tuNgay = formatDate(chiTietDonHang.value.tuNgay)

                                    if (tuNgay === today) {
                                        const formData = new FormData()
                                        formData.append('trangThaiHoatDong', 'Đang cho thuê')
                                        await updateXeMay(xeMay.value.maXeMay, formData)
                                    }

                                    await router.push({
                                        name: 'ChiTietDonHangKhachHangView',
                                        params: {
                                            trangThaiDonHang: 'Chờ xử lý',
                                            maDonHang: responseDonHang.result.maDonHang
                                        }
                                    })
                                }
                            }
                        }
                    }
                } catch (error) {
                    console.error('Lỗi khi cập nhật: ', error)
                } finally {
                    isLoading.value = false
                }
            }

            return {
                showMore,
                visibleCount,
                xeMays,
                tenQuanHuyens,
                tenPhuongXas,
                minDenNgay,
                formattedTongTien,
                tongTien,
                handleThueXe,
                isLoading,
                isError,
                messageError,
                isErrorEmail,
                messageEmail,
                isErrorSoDienThoai,
                messageSoDienThoai,
                isErrorSoGPLX,
                messageSoGPLX,
                isErrorSoCCCD,
                messageSoCCCD,
                maXeMay,
                xeMay,
                danhMucXe,
                chuCuaHang,
                nguoiDungChuCuaHang,
                formattedGiaThue,
                anhXeMays,
                khachHang,
                maQuanHuyens,
                nguoiDungKhachHang,
                donHang,
                chiTietDonHang,
                minDate
            }
        }
    })
</script>

<style>
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
