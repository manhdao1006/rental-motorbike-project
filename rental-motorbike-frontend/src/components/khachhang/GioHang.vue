<template>
    <div class="container-xl mt-4" style="font-family: Arial">
        <div class="row bg-white p-3">
            <div class="text-uppercase card-price fw-bolder fs-5 mt-2 mb-2 pb-0 ps-2">
                <span>Thông tin đơn hàng</span>
            </div>
            <div v-if="cart.length === 0" class="text-center text-muted fst-italic">
                <span>Giỏ hàng của bạn trống</span>
            </div>
            <div
                class="row border-bottom border-black p-3"
                v-for="(item, index) in cart"
                :key="index"
            >
                <div class="col-3 d-flex flex-column justify-content-center">
                    <router-link
                        :to="{
                            name: 'ChiTietXeMayTrangChuView',
                            params: { maXeMay: item.maXeMay }
                        }"
                    >
                        <img :src="item.anhXeMay" width="200px" height="120px" />
                    </router-link>
                </div>
                <div
                    class="col-6 d-flex flex-column justify-content-center border-start border-black"
                >
                    <router-link
                        :to="{
                            name: 'ChiTietXeMayTrangChuView',
                            params: { maXeMay: item.maXeMay }
                        }"
                    >
                        <div class="text-black">
                            <strong>{{ item.tenXe }}</strong>
                        </div>
                        <div class="text-black">{{ item.loaiXe }}</div></router-link
                    >
                </div>
                <div
                    class="col-2 d-flex flex-column justify-content-center border-black border-start border-end"
                >
                    <router-link
                        :to="{
                            name: 'ChiTietXeMayTrangChuView',
                            params: { maXeMay: item.maXeMay }
                        }"
                    >
                        <div style="color: #dc7916">
                            <strong>{{ formattedGiaThue(item.giaThue) }} VNĐ/Ngày</strong>
                        </div></router-link
                    >
                </div>
                <div class="col-1 d-flex flex-column justify-content-center">
                    <div class="text-end">
                        <a
                            class="text-danger"
                            href=""
                            title="Xóa"
                            @click="removeFromCart(item.maXeMay)"
                            >Xóa</a
                        >
                    </div>
                </div>
            </div>
            <div v-if="cart.length > 0 && !hienFormDatThue" class="row mt-3 mb-3">
                <div class="text-end">
                    <button
                        type="button"
                        class="btn btn-primary"
                        title="Đặt thuê xe"
                        @click="hienFormDatThue = !hienFormDatThue"
                    >
                        Đặt thuê xe
                    </button>
                </div>
            </div>
            <div v-if="hienFormDatThue" class="row mt-4">
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
                        <label for="diaChiGiaoXe" class="form-label"
                            >Địa chỉ giao xe<span class="text-danger">*</span></label
                        >
                        <textarea
                            v-model="donHang.diaChiGiaoXe"
                            type="text"
                            class="form-control"
                            id="diaChiGiaoXe"
                        ></textarea>
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
                        ></textarea>
                    </div>
                </div>
            </div>
            <div
                v-if="chiTietDonHang.tuNgay && chiTietDonHang.denNgay && hienFormDatThue"
                class="card-price fs-5 bg-silver p-3"
            >
                Tổng tiền: {{ formattedTongTien(tongTien) }} VNĐ
            </div>
        </div>
        <div v-if="hienFormDatThue" class="row bg-white mt-3 p-3">
            <div class="text-uppercase card-price fw-bolder fs-5 mt-2 mb-2 pb-0 ps-2">
                <span>Thông tin khách hàng</span>
            </div>
            <div class="row">
                <div v-if="isError" class="alert alert-danger">
                    {{ messageError }}
                </div>
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
    <PopupLoading :isLoading="isLoadingPage" />
</template>

<script lang="ts">
    import { useDate } from '@/composables/useDate'
    import {
        getPhuongXaByMaPhuongXa,
        getQuanHuyenByMaPhuongXa,
        uploadWord
    } from '@/services/authService'
    import { addChiTietDonHang } from '@/services/chiTietDonHangService'
    import { getChuCuaHangByMaNguoiDung } from '@/services/chuCuaHangService'
    import { addDonHang } from '@/services/donHangService'
    import { getKhachHangByMaKhachHang, updateKhachHang } from '@/services/khachHangService'
    import { getMaNguoiDung } from '@/services/localStorageService'
    import { getXeMays } from '@/services/xeMayService'
    import { computed, defineComponent, onMounted, ref } from 'vue'
    import { useRouter } from 'vue-router'
    import PopupLoading from '../dungchung/PopupLoading.vue'

    export default defineComponent({
        name: 'GioHang',
        components: {
            PopupLoading
        },
        setup() {
            const visibleRows = ref(4)
            const itemsPerRow = 4
            const visibleCount = computed(() => visibleRows.value * itemsPerRow)
            const xeMays = ref<Record<string, string>[]>([])
            const tenQuanHuyens = ref<Record<string, string>>({})
            const tenPhuongXas = ref<Record<string, string>>({})
            const hienFormDatThue = ref(false)
            const router = useRouter()
            const xeMay = ref<Record<string, string>>({})
            const danhMucXe = ref<Record<string, string>>({})
            const chuCuaHang = ref<Record<string, string>>({})
            const nguoiDungChuCuaHang = ref<Record<string, string>>({})
            const khachHang = ref<Record<string, string>>({})
            const nguoiDungKhachHang = ref<Record<string, string>>({})
            const donHang = ref<Record<string, string>>({})
            const chiTietDonHang = ref<Record<string, string>>({})
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
            const isLoadingPage = ref(true)
            const cart = ref<Record<string, string>[]>([])
            const minDate = new Date().toISOString().split('T')[0]
            const minDenNgay = computed(() => {
                return chiTietDonHang.value.tuNgay || minDate
            })

            const showMore = () => {
                visibleRows.value += 4
            }

            const formattedTongTien = (gia: number) => {
                return new Intl.NumberFormat('vi-VN').format(gia)
            }

            const formattedGiaThue = (giaThue: string) => {
                if (!giaThue) return '0'
                const numberGiaThue = Number(giaThue)
                return numberGiaThue.toLocaleString('vi-VN')
            }

            const removeFromCart = (maXeMay: string) => {
                cart.value = cart.value.filter((item) => item.maXeMay !== maXeMay)
                const cartKey = `cart_user_${getMaNguoiDung()}`
                localStorage.setItem(cartKey, JSON.stringify(cart.value))
            }

            const tongTien = computed(() => {
                return cart.value.reduce((total, item: Record<string, unknown>) => {
                    const giaThue = item.giaThue as number
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

                    const tongTienXe = soNgayThue > 0 ? soNgayThue * giaThue : 0

                    return total + tongTienXe
                }, 0)
            })

            const fetchChuCuaHang = async () => {
                if (cart.value.length > 0) {
                    const maChuCuaHang = cart.value[0].maChuCuaHang
                    const response = await getChuCuaHangByMaNguoiDung(String(maChuCuaHang))
                    nguoiDungChuCuaHang.value = response.nguoiDung
                    chuCuaHang.value = response.chuCuaHang

                    if (response.success) {
                        console.log(response.result)
                    } else {
                        console.error('Không tìm thấy thông tin chủ cửa hàng')
                    }
                } else {
                    console.error('Giỏ hàng trống')
                }
            }

            const fetchKhachHang = async () => {
                const response = await getKhachHangByMaKhachHang(getMaNguoiDung())
                khachHang.value = response.khachHang
                nguoiDungKhachHang.value = response.nguoiDung
            }

            const fetchXeMays = async () => {
                const response = await getXeMays()
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
                            !cart.value.some((c) => c.maXeMay === item.xeMay.maXeMay)
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

            onMounted(async () => {
                const cartKey = `cart_user_${getMaNguoiDung()}`
                const storedCart = localStorage.getItem(cartKey)
                if (storedCart) {
                    cart.value = JSON.parse(storedCart)
                }
                await Promise.all([fetchXeMays(), fetchKhachHang(), fetchChuCuaHang()])
                isLoadingPage.value = false
            })

            const handleThueXe = async () => {
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
                            danhSachXe: cart.value.map((xe) => ({
                                tenXe: xe.tenXe,
                                loaiXe: xe.loaiXe,
                                danhMucXe: xe.danhMucXe,
                                mauXe: xe.mauXe,
                                bienSoXe: xe.bienSoXe,
                                soMay: xe.soMay,
                                soKhung: xe.soKhung,
                                giaThue: formattedGiaThue(xe.giaThue),
                                tuNgay: useDate(chiTietDonHang.value.tuNgay),
                                denNgay: useDate(chiTietDonHang.value.denNgay)
                            })),
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
                                const maDonHang = responseDonHang.result.maDonHang
                                let allSuccess = true

                                for (const xe of cart.value) {
                                    const chiTiet: Record<string, string> = {
                                        maDonHang,
                                        maXeMay: xe.maXeMay,
                                        giaThue: xe.giaThue,
                                        tuNgay: chiTietDonHang.value.tuNgay,
                                        denNgay: chiTietDonHang.value.denNgay
                                    }

                                    const formDataChiTiet = new FormData()
                                    Object.entries(chiTiet).forEach(([key, value]) => {
                                        formDataChiTiet.append(key, value || '')
                                    })

                                    await addChiTietDonHang(formDataChiTiet)
                                    allSuccess = false
                                }

                                if (allSuccess) {
                                    const priceKey = `cart_price_${getMaNguoiDung()}`
                                    localStorage.setItem(priceKey, tongTien.value.toString())

                                    const cartKey = `cart_user_${getMaNguoiDung()}`
                                    localStorage.removeItem(cartKey)
                                    cart.value = []
                                    alert('Đặt thuê thành công!')
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
                tongTien,
                formattedTongTien,
                minDate,
                minDenNgay,
                removeFromCart,
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
                xeMay,
                danhMucXe,
                chuCuaHang,
                nguoiDungChuCuaHang,
                formattedGiaThue,
                anhXeMays,
                khachHang,
                nguoiDungKhachHang,
                donHang,
                chiTietDonHang,
                cart,
                hienFormDatThue,
                isLoadingPage
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
