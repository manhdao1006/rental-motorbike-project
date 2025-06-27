<template>
    <div class="container-fluid">
        <div class="row justify-content-center">
            <div class="btn-group">
                <router-link
                    :to="{
                        name: 'DanhSachDonHangNhanVienView',
                        params: { trangThaiDonHang: 'Chờ xử lý' }
                    }"
                    class="btn btn-primary text-uppercase border-start"
                    :class="{ active: trangThaiDonHangParams === 'Chờ xử lý' }"
                    >Chờ xử lý</router-link
                >
                <router-link
                    :to="{
                        name: 'DanhSachDonHangNhanVienView',
                        params: { trangThaiDonHang: 'Đang giao xe' }
                    }"
                    class="btn btn-primary text-uppercase border-start"
                    :class="{ active: trangThaiDonHangParams === 'Đang giao xe' }"
                    >Đang giao xe</router-link
                >
                <router-link
                    :to="{
                        name: 'DanhSachDonHangNhanVienView',
                        params: { trangThaiDonHang: 'Đã giao xe' }
                    }"
                    class="btn btn-primary text-uppercase border-start"
                    :class="{ active: trangThaiDonHangParams === 'Đã giao xe' }"
                    >Đã giao xe</router-link
                >
                <router-link
                    :to="{
                        name: 'DanhSachDonHangNhanVienView',
                        params: { trangThaiDonHang: 'Đã trả xe' }
                    }"
                    class="btn btn-primary text-uppercase border-start"
                    :class="{ active: trangThaiDonHangParams === 'Đã trả xe' }"
                    >Đã trả xe</router-link
                >
                <router-link
                    :to="{
                        name: 'DanhSachDonHangNhanVienView',
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
                        name: 'DanhSachDonHangNhanVienView',
                        params: { trangThaiDonHang: trangThaiDonHangParams }
                    }"
                >
                    <i class="fas fa-chevron-circle-left"></i>
                    <span class="ps-1">Quay lại danh sách</span>
                </router-link>
            </div>
        </div>
        <div class="row" v-if="trangThaiDonHangParams === 'Đang giao xe'">
            <div ref="mapRef" style="height: 300px; width: 100%"></div>
            <p v-if="loading">Đang xử lý...</p>
            <p v-else-if="error">Lỗi: {{ error }}</p>
        </div>
        <div class="row m-0">
            <div v-if="isError" class="alert alert-danger">
                {{ messageError }}
            </div>
            <div class="row">
                <div class="col-8">
                    <div class="text-black fw-bolder">Thông tin đơn hàng</div>
                    <table class="table table-hover table-bordered my-0">
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
                                    >
                                        {{ formattedGiaThue((item as any).xeMay.giaThue) }} VNĐ
                                    </router-link>
                                </td>
                                <td>
                                    <router-link
                                        :to="{ name: 'CapNhatTinhTrangXeView', params: { trangThaiDonHang: trangThaiDonHangParams, maDonHang: donHang.maDonHang, maXeMay: (item as any).xeMay.maXeMay }}"
                                    >
                                        {{ formatDate(String((item as any).tuNgay)) }}
                                    </router-link>
                                </td>
                                <td>
                                    <router-link
                                        :to="{ name: 'CapNhatTinhTrangXeView', params: { trangThaiDonHang: trangThaiDonHangParams, maDonHang: donHang.maDonHang, maXeMay: (item as any).xeMay.maXeMay }}"
                                    >
                                        {{ formatDate(String((item as any).denNgay)) }}
                                    </router-link>
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
                                    disabled
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
                                    :disabled="trangThaiDonHangParams !== 'Đã giao xe'"
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
                        disabled
                    ></textarea>
                </div>
            </div>
            <div
                class="text-center mt-3 mb-3"
                v-if="
                    trangThaiDonHangParams !== 'Đang giao xe' &&
                    trangThaiDonHangParams !== 'Đã giao xe'
                "
            >
                <button
                    v-if="!isAllXeMayAvailable"
                    type="button"
                    class="btn btn-success"
                    title="Xác nhận"
                    @click.prevent="handleXacNhan"
                    :disabled="isLoading"
                >
                    Xác nhận
                </button>
            </div>
            <div class="text-center mt-3 mb-3" v-if="trangThaiDonHangParams === 'Đang giao xe'">
                <button
                    v-if="!isAllXeMayAvailable"
                    type="button"
                    class="btn btn-success"
                    title="Xác nhận"
                    @click.prevent="handleGiaoXe"
                    :disabled="isLoading"
                >
                    Đã giao xe
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
    import { getXeMayByMaXeMay } from '@/services/xeMayService'
    import L from 'leaflet'
    import 'leaflet-routing-machine'
    import 'leaflet/dist/leaflet.css'
    import { computed, defineComponent, nextTick, onMounted, ref } from 'vue'
    import { useRoute, useRouter } from 'vue-router'

    export default defineComponent({
        name: 'ChiTietDonHangNhanVien',
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
            const chuCuaHang = ref<Record<string, null>>({})
            const xeMays = ref<Record<string, string>[]>([])
            const chiTietDonHangs = ref<Record<string, string>[]>([])
            const nhanViens = ref<Record<string, unknown>[]>([])
            const isLoading = ref(false)
            const isLoadingPage = ref(true)
            const trangThaiDonHangParams = String(route.params.trangThaiDonHang)
            const startAddress = ref<string>('')
            const endAddress = ref<string>('')

            const mapRef = ref<HTMLElement | null>(null)
            const loading = ref(false)
            const error = ref('')
            let map: L.Map
            // eslint-disable-next-line @typescript-eslint/no-explicit-any
            let routingControl: any

            const startIcon = L.divIcon({
                className: 'custom-icon',
                html: '<i class="fas fa-map-marker-alt" style="font-size: 30px;"></i>',
                iconSize: [30, 30],
                iconAnchor: [15, 30],
                popupAnchor: [0, -30]
            })

            const endIcon = L.divIcon({
                className: 'custom-icon',
                html: '<i class="fas fa-map-marker-alt" style="color:red; font-size: 30px;"></i>',
                iconSize: [30, 30],
                iconAnchor: [15, 30],
                popupAnchor: [0, -30]
            })

            const initMap = async () => {
                await nextTick()

                if (mapRef.value) {
                    map = L.map(mapRef.value).setView([16.0674, 108.2222], 14)
                    L.tileLayer('https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png').addTo(map)
                }
            }

            onMounted(async () => {
                await Promise.all([fetchDonHang(), fetchNhanViens()])
                isLoadingPage.value = false

                await nextTick()
                await initMap()

                if (trangThaiDonHangParams === 'Đang giao xe') {
                    startAddress.value = String(chuCuaHang.value.diaChiCuaHang)
                    endAddress.value = String(donHang.value.diaChiGiaoXe)

                    loading.value = true
                    error.value = ''

                    try {
                        const [start, end] = await Promise.all([
                            geocode(startAddress.value),
                            geocode(endAddress.value)
                        ])

                        L.marker([start.lat, start.lng], { icon: startIcon })
                            .addTo(map)
                            .bindTooltip('Vị trí của bạn', { permanent: true, direction: 'right' })

                        L.marker([end.lat, end.lng], { icon: endIcon })
                            .addTo(map)
                            .bindTooltip('Khách hàng', { permanent: true, direction: 'right' })

                        if (routingControl) {
                            routingControl.setWaypoints([
                                L.latLng(start.lat, start.lng),
                                L.latLng(end.lat, end.lng)
                            ])
                        } else {
                            routingControl = L.Routing.control({
                                waypoints: [
                                    L.latLng(start.lat, start.lng),
                                    L.latLng(end.lat, end.lng)
                                ],
                                createMarker: () => null,
                                addWaypoints: false,
                                draggableWaypoints: false,
                                fitSelectedRoutes: true,
                                showAlternatives: false,
                                routeWhileDragging: false,
                                lineOptions: {
                                    styles: [{ color: 'blue', weight: 5 }],
                                    extendToWaypoints: false,
                                    missingRouteTolerance: 0
                                },
                                show: false
                                // eslint-disable-next-line @typescript-eslint/no-explicit-any
                            } as any)

                            routingControl.addTo(map)

                            routingControl.on('routesfound', () => {
                                const container = document.querySelector(
                                    '.leaflet-routing-container'
                                )
                                if (container) container.remove()
                            })
                        }

                        map.setView([start.lat, start.lng], 14)
                        // eslint-disable-next-line @typescript-eslint/no-explicit-any
                    } catch (err: any) {
                        error.value = err.message
                    }

                    loading.value = false
                }
            })

            const geocode = async (address: string) => {
                const fullAddress = address.includes('Đà Nẵng')
                    ? address
                    : `${address}, Đà Nẵng, Việt Nam`

                // Giới hạn vùng tìm kiếm trong Đà Nẵng
                const viewbox = '108.1,15.9,108.3,16.15'
                const url = `https://nominatim.openstreetmap.org/search?format=json&q=${encodeURIComponent(
                    fullAddress
                )}&viewbox=${viewbox}&bounded=1`

                const response = await fetch(url)
                const data = await response.json()
                if (!data.length)
                    throw new Error(`Không tìm thấy địa chỉ trong khu vực Đà Nẵng: ${address}`)

                return {
                    lat: parseFloat(data[0].lat),
                    lng: parseFloat(data[0].lon)
                }
            }

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
                chuCuaHang.value = responseDonHang.chuCuaHang
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
                nhanViens.value = response.map((item: { nguoiDung: object }) => item.nguoiDung)
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

            const handleGiaoXe = async () => {
                isLoading.value = true
                try {
                    const formData = new FormData()
                    formData.append('trangThaiDonHang', 'Đã giao xe')

                    const response = await updateDonHang(String(donHang.value.maDonHang), formData)
                    if (response.success) {
                        alert('Giao xe thành công!')
                        await router.push({
                            name: 'DanhSachDonHangNhanVienView',
                            params: { trangThaiDonHang: 'Đã giao xe' }
                        })
                    }
                } catch (error) {
                    console.error('Lỗi khi cập nhật: ', error)
                } finally {
                    isLoading.value = false
                }
            }

            const handleXacNhan = async () => {
                if (!donHang.value.ngayGiaoXe) {
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
                    if (trangThaiDonHangParams === 'Chờ xử lý') {
                        formData.append('trangThaiDonHang', 'Đang giao xe')
                    }
                    if (trangThaiDonHangParams === 'Đã trả xe') {
                        formData.append('trangThaiDonHang', 'Đã trả xe')
                    }

                    const response = await updateDonHang(String(donHang.value.maDonHang), formData)
                    if (response.success) {
                        await router.push({
                            name: 'DanhSachDonHangNhanVienView',
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
                mapRef,
                loading,
                error,
                isLoadingPage,
                isAllXeMayAvailable,
                minGiaoXeDate,
                maxGiaoXeDate,
                minTraXeDate,
                maxTraXeDate,
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
                handleXacNhan,
                handleGiaoXe,
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
