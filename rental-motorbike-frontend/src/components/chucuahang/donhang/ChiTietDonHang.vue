<template>
    <div class="container-fluid">
        <div class="row justify-content-center">
            <div class="btn-group">
                <router-link
                    :to="{
                        name: 'DanhSachDonHangView',
                        params: { trangThaiDonHang: 'Chờ xử lý' }
                    }"
                    class="btn btn-primary text-uppercase border-start border-end"
                    :class="{ active: trangThaiDonHangParams === 'Chờ xử lý' }"
                    >Chờ xử lý</router-link
                >
                <router-link
                    :to="{
                        name: 'DanhSachDonHangView',
                        params: { trangThaiDonHang: 'Đang giao xe' }
                    }"
                    class="btn btn-primary text-uppercase border-start"
                    :class="{ active: trangThaiDonHangParams === 'Đang giao xe' }"
                    >Đang giao xe</router-link
                >
                <router-link
                    :to="{
                        name: 'DanhSachDonHangView',
                        params: { trangThaiDonHang: 'Đã giao xe' }
                    }"
                    class="btn btn-primary text-uppercase border-start"
                    :class="{ active: trangThaiDonHangParams === 'Đã giao xe' }"
                    >Đã giao xe</router-link
                >
                <router-link
                    :to="{
                        name: 'DanhSachDonHangView',
                        params: { trangThaiDonHang: 'Đã trả xe' }
                    }"
                    class="btn btn-primary text-uppercase border-start"
                    :class="{ active: trangThaiDonHangParams === 'Đã trả xe' }"
                    >Đã trả xe</router-link
                >
                <router-link
                    :to="{
                        name: 'DanhSachDonHangView',
                        params: { trangThaiDonHang: 'Đã hủy' }
                    }"
                    class="btn btn-primary text-uppercase border-start"
                    :class="{ active: trangThaiDonHangParams === 'Đã hủy' }"
                    >Đã hủy</router-link
                >
            </div>
        </div>
        <div class="row justify-content-evenly m-0 mt-3 mb-3">
            <div class="card-header col-xl-6">
                <h5 class="card-title mb-0">
                    Xử lý đơn hàng {{ donHang.maDonHang }} >>
                    <a :href="donHang?.tepDinhKem ?? '#'">Tải hợp đồng</a>
                </h5>
            </div>
            <div class="card-header col-xl-6 text-end">
                <router-link
                    class="text-success"
                    :to="{
                        name: 'DanhSachDonHangView',
                        params: { trangThaiDonHang: trangThaiDonHangParams }
                    }"
                >
                    <i class="fas fa-chevron-circle-left"></i>
                    <span class="ps-1">Quay lại danh sách</span>
                </router-link>
            </div>
        </div>
        <div class="row m-0">
            <div v-if="isError" class="alert alert-danger">
                {{ messageError }}
            </div>
            <div class="row">
                <div class="col-6" v-if="trangThaiDonHangParams !== 'Đã trả xe'">
                    <div class="mb-3">
                        <label for="trangThaiDonHang" class="form-label"
                            >Trạng thái đơn hàng<span class="text-danger">*</span></label
                        >
                        <select
                            class="form-select"
                            aria-label="Default select example"
                            v-model="donHang.trangThaiDonHang"
                        >
                            <option selected disabled>Chọn trạng thái</option>
                            <option value="Chờ xử lý">Chờ xử lý</option>
                            <option value="Chờ xử lý">Duyệt đơn</option>
                            <option value="Đã hủy">Từ chối</option>
                        </select>
                    </div>
                </div>
                <div class="col-6">
                    <div class="mb-3" v-if="donHang.trangThaiDonHang === 'Đã hủy'">
                        <label for="lyDoHuy" class="form-label"
                            >Lý do từ chối<span class="text-danger">*</span></label
                        >
                        <input
                            v-model="donHang.lyDoHuy"
                            type="text"
                            class="form-control"
                            id="lyDoHuy"
                        />
                    </div>
                    <div class="mb-3" v-if="donHang.trangThaiDonHang === 'Chờ xử lý'">
                        <label for="maNhanVien" class="form-label"
                            >Nhân viên giao xe<span class="text-danger">*</span></label
                        >
                        <select
                            v-model="donHang.maNhanVien"
                            class="form-select"
                            aria-label="Default select example"
                        >
                            <option selected disabled>Chọn nhân viên</option>
                            <template v-for="nhanVien in nhanViens" :key="nhanVien.maNguoiDung">
                                <option :value="nhanVien.maNguoiDung">
                                    {{ nhanVien.hoVaTen }}
                                </option>
                            </template>
                        </select>
                    </div>
                </div>
            </div>
            <div class="row">
                <div class="col-8">
                    <div class="text-black fw-bolder">Thông tin đơn hàng</div>
                    <table class="table table-bordered my-0">
                        <thead>
                            <tr>
                                <th>STT</th>
                                <th>Tên xe</th>
                                <th>Biển số xe</th>
                                <th>Giá thuê</th>
                                <th>Từ ngày</th>
                                <th>Đến ngày</th>
                            </tr>
                        </thead>
                        <tbody>
                            <tr v-for="(item, index) in xeMays" :key="(item as any).xeMay.maXeMay">
                                <td>
                                    <router-link
                                        :to="{ name: 'CapNhatTinhTrangXeView', params: { trangThaiDonHang: trangThaiDonHangParams, maDonHang: donHang.maDonHang, maXeMay: (item as any).xeMay.maXeMay }}"
                                    >
                                        {{ index + 1 }}
                                    </router-link>
                                </td>
                                <td>
                                    <router-link
                                        :to="{ name: 'CapNhatTinhTrangXeView', params: { trangThaiDonHang: trangThaiDonHangParams, maDonHang: donHang.maDonHang, maXeMay: (item as any).xeMay.maXeMay }}"
                                    >
                                        {{ (item as any).xeMay.tenXe }}

                                        <br /><img
                                            :src="(item as any).anhXeMays[0].duongDan"
                                            :alt="(item as any).xeMay.tenXe"
                                            width="180px"
                                            height="120px"
                                        />
                                    </router-link>
                                </td>
                                <td>
                                    <router-link
                                        :to="{ name: 'CapNhatTinhTrangXeView', params: { trangThaiDonHang: trangThaiDonHangParams, maDonHang: donHang.maDonHang, maXeMay: (item as any).xeMay.maXeMay }}"
                                    >
                                        {{ (item as any).xeMay.bienSoXe }}
                                    </router-link>
                                </td>
                                <td>
                                    <router-link
                                        :to="{ name: 'CapNhatTinhTrangXeView', params: { trangThaiDonHang: trangThaiDonHangParams, maDonHang: donHang.maDonHang, maXeMay: (item as any).xeMay.maXeMay }}"
                                        >{{
                                            formattedGiaThue((item as any).xeMay.giaThue)
                                        }}
                                        VNĐ</router-link
                                    >
                                </td>
                                <td>
                                    <router-link
                                        :to="{ name: 'CapNhatTinhTrangXeView', params: { trangThaiDonHang: trangThaiDonHangParams, maDonHang: donHang.maDonHang, maXeMay: (item as any).xeMay.maXeMay }}"
                                        >{{ formatDate(String((item as any).tuNgay)) }}</router-link
                                    >
                                </td>
                                <td>
                                    <router-link
                                        :to="{ name: 'CapNhatTinhTrangXeView', params: { trangThaiDonHang: trangThaiDonHangParams, maDonHang: donHang.maDonHang, maXeMay: (item as any).xeMay.maXeMay }}"
                                        >{{
                                            formatDate(String((item as any).denNgay))
                                        }}</router-link
                                    >
                                </td>
                            </tr>
                        </tbody>
                    </table>
                    <div class="card-price">Tổng tiền: {{ formattedTongTien(tongTien) }} VNĐ</div>
                </div>
                <div class="col-4 bg-white p-4">
                    <div class="text-black fw-bolder mb-3 text-center">Thông tin khách hàng</div>
                    <div class="row">
                        <div class="col-4 title-user">Họ và tên:</div>
                        <div class="col-8 content-user">{{ nguoiDungKhachHang.hoVaTen }}</div>
                    </div>
                    <div class="row">
                        <div class="col-4 title-user">Email:</div>
                        <div class="col-8 content-user">{{ nguoiDungKhachHang.email }}</div>
                    </div>
                    <div class="row">
                        <div class="col-4 title-user">Giới tính:</div>
                        <div class="col-8 content-user">{{ nguoiDungKhachHang.gioiTinh }}</div>
                    </div>
                    <div class="row">
                        <div class="col-4 title-user">Ngày sinh:</div>
                        <div class="col-8 content-user">
                            {{
                                nguoiDungKhachHang.ngaySinh
                                    ? formatDate(String(nguoiDungKhachHang.ngaySinh))
                                    : 'NaN'
                            }}
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-4 title-user">SĐT:</div>
                        <div class="col-8 content-user">{{ nguoiDungKhachHang.soDienThoai }}</div>
                    </div>
                    <div class="row">
                        <div class="col-4 title-user">Số CCCD:</div>
                        <div class="col-8 content-user">{{ nguoiDungKhachHang.soCCCD }}</div>
                    </div>
                    <div class="row">
                        <div class="col-4 title-user">Số GPLX:</div>
                        <div class="col-8 content-user">{{ khachHang.soGPLX }}</div>
                    </div>
                </div>
            </div>
            <div class="row bg-white mt-2 ms-0 pt-3 pb-3">
                <div class="col-6">
                    <div class="row">
                        <div class="col-6">
                            <div class="mb-3">
                                <label for="ngayGiaoXe" class="form-label"
                                    >Ngày giao xe<span class="text-danger">*</span></label
                                >
                                <input
                                    v-model="donHang.ngayGiaoXe"
                                    type="datetime-local"
                                    class="form-control"
                                    id="ngayGiaoXe"
                                    :min="minGiaoXeDate"
                                    :max="maxGiaoXeDate"
                                />
                            </div>
                        </div>
                        <div class="col-6">
                            <div class="mb-3">
                                <label for="ngayTraXe" class="form-label">Ngày trả xe</label>
                                <input
                                    v-model="donHang.ngayTraXe"
                                    type="datetime-local"
                                    class="form-control"
                                    id="ngayTraXe"
                                    :min="minTraXeDate"
                                    :max="maxTraXeDate"
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
                            disabled
                        >
                            <option selected disabled>Chọn phương thức nhận xe</option>
                            <option value="Nhận tại cửa hàng">Nhận tại cửa hàng</option>
                            <option value="Giao xe tận nơi">Giao xe tận nơi</option>
                        </select>
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
                            disabled
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
                            disabled
                        ></textarea>
                    </div>
                </div>
                <div class="mb-3">
                    <label for="diaChiGiaoXe" class="form-label"
                        >Địa chỉ giao xe<span class="text-danger">*</span></label
                    >
                    <textarea
                        :value="donHang.diaChiGiaoXe || nguoiDungKhachHang.diaChi"
                        type="text"
                        class="form-control"
                        id="diaChiGiaoXe"
                        rows="1"
                        disabled
                    ></textarea>
                </div>
            </div>
            <div class="text-center mt-3">
                <button
                    v-if="donHang.trangThaiDonHang === 'Đã hủy'"
                    type="button"
                    class="btn btn-success"
                    title="Xác nhận"
                    @click.prevent="handleTuChoi"
                    :disabled="isLoading"
                >
                    Xác nhận
                </button>
                <button
                    v-if="donHang.trangThaiDonHang === 'Chờ xử lý'"
                    type="button"
                    class="btn btn-success"
                    title="Duyệt đơn"
                    @click.prevent="handleDuyet"
                    :disabled="isLoading"
                >
                    Duyệt đơn
                </button>
                <button
                    v-if="donHang.trangThaiDonHang === 'Đã trả xe' && !isAllXeMayAvailable"
                    type="button"
                    class="btn btn-success"
                    title="Xác nhận"
                    @click.prevent="handleXacNhan"
                    :disabled="isLoading"
                >
                    Xác nhận
                </button>
            </div>
        </div>
    </div>
    <PopupLoading :isLoading="isLoading" />
    <PopupLoading :isLoading="isLoadingPage" />
</template>

<script lang="ts">
    import PopupLoading from '@/components/dungchung/PopupLoading.vue'
    import { useDate } from '@/composables/useDate'
    import { getDonHangByMaDonHang, updateDonHang } from '@/services/donHangService'
    import { getKhachHangByMaNguoiDung } from '@/services/khachHangService'
    import { getMaNguoiDung } from '@/services/localStorageService'
    import { getNhanViensByChuCuaHang } from '@/services/nhanVienService'
    import { getXeMayByMaXeMay, updateXeMay } from '@/services/xeMayService'
    import { computed, defineComponent, onMounted, ref } from 'vue'
    import { useRoute, useRouter } from 'vue-router'

    export default defineComponent({
        name: 'ChiTietDonHang',
        components: {
            PopupLoading
        },
        setup() {
            const route = useRoute()
            const router = useRouter()
            const isError = ref(false)
            const messageError = ref<string>('')
            const donHang = ref<Record<string, null>>({})
            const nguoiDungKhachHang = ref<Record<string, null>>({})
            const khachHang = ref<Record<string, null>>({})
            const xeMays = ref<Record<string, string>[]>([])
            const chiTietDonHangs = ref<Record<string, string>[]>([])
            const nhanViens = ref<Record<string, unknown>[]>([])
            const isLoading = ref(false)
            const isLoadingPage = ref(true)
            const trangThaiDonHangParams = String(route.params.trangThaiDonHang)

            const minGiaoXeDate = computed(() => {
                if (!xeMays.value.length) return undefined
                const tuNgay = new Date(xeMays.value[0].tuNgay)
                tuNgay.setDate(tuNgay.getDate() - 2)
                return tuNgay.toISOString().slice(0, 16)
            })

            const maxGiaoXeDate = computed(() => {
                if (!xeMays.value.length) return undefined
                const tuNgay = new Date(xeMays.value[0].tuNgay)
                return tuNgay.toISOString().slice(0, 16)
            })
            const minTraXeDate = computed(() => {
                if (!xeMays.value.length) return undefined
                const tuNgay = new Date(xeMays.value[0].tuNgay)
                tuNgay.setDate(tuNgay.getDate() + 2)
                tuNgay.setHours(0, 0, 0, 0)
                return tuNgay.toISOString().slice(0, 16)
            })

            const maxTraXeDate = computed(() => {
                if (!xeMays.value.length) return undefined
                const denNgay = new Date(xeMays.value[0].denNgay)
                denNgay.setDate(denNgay.getDate() + 2)
                return denNgay.toISOString().slice(0, 16)
            })

            const formatDate = (date: string) => {
                return useDate(date)
            }

            const formattedGiaThue = (giaThue: string) => {
                if (!giaThue) return '0'
                const numberGiaThue = Number(giaThue)
                return numberGiaThue.toLocaleString('vi-VN')
            }

            const fetchDonHang = async () => {
                const responseDonHang = await getDonHangByMaDonHang(String(route.params.maDonHang))
                donHang.value = responseDonHang.donHang
                khachHang.value = responseDonHang.khachHang
                chiTietDonHangs.value = responseDonHang.chiTietDonHangs

                const responseKhachHang = await getKhachHangByMaNguoiDung(
                    responseDonHang.khachHang.maKhachHang
                )
                nguoiDungKhachHang.value = responseKhachHang.nguoiDung

                const maXeMays = responseDonHang.chiTietDonHangs
                    .map((chiTiet: { maXeMay: string }) => chiTiet.maXeMay)
                    .filter((maXeMay: string) => maXeMay)

                const xeMayPromises = maXeMays.map((maXeMay: string) => getXeMayByMaXeMay(maXeMay))
                const xeMaysData = await Promise.all(xeMayPromises)

                xeMays.value = xeMaysData.map((xe) => {
                    const chiTiet = chiTietDonHangs.value.find(
                        (ct) => ct.maXeMay === xe.xeMay.maXeMay
                    )
                    return {
                        ...xe,
                        tuNgay: chiTiet ? chiTiet.tuNgay : 'NaN',
                        denNgay: chiTiet ? chiTiet.denNgay : 'NaN'
                    }
                })
            }

            const isAllXeMayAvailable = computed(() => {
                // eslint-disable-next-line @typescript-eslint/no-explicit-any
                return xeMays.value.every((item: any) => item.xeMay.trangThaiHoatDong === 'Có sẵn')
            })

            const fetchNhanViens = async () => {
                const response = await getNhanViensByChuCuaHang(getMaNguoiDung())
                nhanViens.value = response
                    .filter(
                        // eslint-disable-next-line @typescript-eslint/no-explicit-any
                        (item: any) =>
                            item.nguoiDung?.trangThaiHoatDong === 'Hoạt động' &&
                            item.nguoiDung?.trangThaiXoa === '1'
                    )
                    .map((item: { nguoiDung: object }) => item.nguoiDung)
            }

            const tongTien = computed(() => {
                return xeMays.value.reduce((total, item: Record<string, unknown>) => {
                    const xeMay = item.xeMay as Record<string, unknown>

                    const giaThue = xeMay.giaThue as number
                    const tuNgay = item.tuNgay
                    const denNgay = item.denNgay

                    const validTuNgay =
                        tuNgay &&
                        (typeof tuNgay === 'string' ||
                            typeof tuNgay === 'number' ||
                            tuNgay instanceof Date)
                    const validDenNgay =
                        denNgay &&
                        (typeof denNgay === 'string' ||
                            typeof denNgay === 'number' ||
                            denNgay instanceof Date)

                    const soNgayThue =
                        validTuNgay && validDenNgay
                            ? (new Date(denNgay).getTime() - new Date(tuNgay).getTime()) /
                              (1000 * 3600 * 24)
                            : 0

                    const tongTienXe = soNgayThue * giaThue

                    return total + tongTienXe
                }, 0)
            })

            const formattedTongTien = (gia: number) => {
                return new Intl.NumberFormat('vi-VN').format(gia)
            }

            onMounted(async () => {
                await Promise.all([fetchDonHang(), fetchNhanViens()])
                isLoadingPage.value = false
            })

            const handleXacNhan = async () => {
                isLoading.value = true
                try {
                    const formData = new FormData()
                    Object.entries(donHang.value).forEach(([key, value]) => {
                        if (
                            key !== 'chiTietDonHangs' &&
                            key !== 'khieuNais' &&
                            key !== 'ngayTao' &&
                            key !== 'tepDinhKem' &&
                            key !== 'trangThaiXoa' &&
                            value !== undefined
                        ) {
                            formData.append(key, value || '')
                        }
                    })

                    const response = await updateDonHang(String(donHang.value.maDonHang), formData)
                    if (response.success) {
                        const formData = new FormData()
                        formData.append('trangThaiHoatDong', 'Có sẵn')
                        for (let xe of xeMays.value) {
                            const responseXeMay = await updateXeMay(
                                (xe.xeMay as unknown as { maXeMay: string }).maXeMay,
                                formData
                            )
                            if (responseXeMay.success) {
                                await router.push({
                                    name: 'DanhSachDonHangView',
                                    params: { trangThaiDonHang: 'Chờ xử lý' }
                                })
                            }
                        }
                    }
                } catch (error) {
                    console.error('Lỗi khi cập nhật: ', error)
                } finally {
                    isLoading.value = false
                }
            }

            const handleTuChoi = async () => {
                if (!donHang.value.trangThaiDonHang || !donHang.value.lyDoTuChoi) {
                    isError.value = true
                    messageError.value = 'Vui lòng nhập đầy đủ các trường dữ liệu!'
                    setTimeout(() => {
                        isError.value = false
                        messageError.value = ''
                    }, 3000)
                    return
                }

                isLoading.value = true
                try {
                    const formData = new FormData()
                    Object.entries(donHang.value).forEach(([key, value]) => {
                        if (
                            key !== 'chiTietDonHangs' &&
                            key !== 'khieuNais' &&
                            key !== 'ngayTao' &&
                            key !== 'tepDinhKem' &&
                            key !== 'trangThaiXoa' &&
                            value !== undefined
                        ) {
                            formData.append(key, value || '')
                        }
                    })

                    const response = await updateDonHang(String(donHang.value.maDonHang), formData)
                    if (response.success) {
                        const formData = new FormData()
                        formData.append('trangThaiHoatDong', 'Có sẵn')
                        for (let xe of xeMays.value) {
                            const responseXeMay = await updateXeMay(
                                (xe.xeMay as unknown as { maXeMay: string }).maXeMay,
                                formData
                            )
                            if (responseXeMay.success) {
                                await router.push({
                                    name: 'DanhSachDonHangView',
                                    params: { trangThaiDonHang: 'Chờ xử lý' }
                                })
                            }
                        }
                    }
                } catch (error) {
                    console.error('Lỗi khi cập nhật: ', error)
                } finally {
                    isLoading.value = false
                }
            }

            const handleDuyet = async () => {
                if (
                    !donHang.value.trangThaiDonHang ||
                    !donHang.value.ngayGiaoXe ||
                    !donHang.value.diaChiGiaoXe ||
                    !donHang.value.maNhanVien
                ) {
                    isError.value = true
                    messageError.value = 'Vui lòng nhập đầy đủ các trường dữ liệu!'
                    setTimeout(() => {
                        isError.value = false
                        messageError.value = ''
                    }, 3000)
                    return
                }

                try {
                    const formData = new FormData()
                    Object.entries(donHang.value).forEach(([key, value]) => {
                        if (
                            key !== 'chiTietDonHangs' &&
                            key !== 'khieuNais' &&
                            key !== 'ngayTao' &&
                            key !== 'tepDinhKem' &&
                            key !== 'trangThaiXoa' &&
                            value !== undefined
                        ) {
                            formData.append(key, value || '')
                        }
                    })

                    const response = await updateDonHang(String(donHang.value.maDonHang), formData)
                    if (response.success) {
                        await router.push({
                            name: 'DanhSachDonHangView',
                            params: { trangThaiDonHang: 'Chờ xử lý' }
                        })
                    }
                } catch (error) {
                    console.error('Lỗi khi cập nhật: ', error)
                } finally {
                    isLoading.value = false
                }
            }

            return {
                isLoadingPage,
                isAllXeMayAvailable,
                handleXacNhan,
                minTraXeDate,
                maxTraXeDate,
                minGiaoXeDate,
                maxGiaoXeDate,
                trangThaiDonHangParams,
                tongTien,
                formatDate,
                formattedGiaThue,
                formattedTongTien,
                isError,
                messageError,
                donHang,
                xeMays,
                nhanViens,
                nguoiDungKhachHang,
                khachHang,
                handleTuChoi,
                handleDuyet,
                isLoading,
                chiTietDonHangs
            }
        }
    })
</script>

<style>
    .title-user {
        font-size: 15px;
        color: #000000;
        opacity: 0.8;
        text-align: right;
    }
    .content-user {
        font-size: 16px;
        color: #000000;
        font-weight: 600;
    }
</style>
