<template>
    <div class="container-fluid row mt-3 bg-white">
        <div class="col-2">
            <ThongTinKhachHang
                :source="nguoiDungKhachHang.duongDan || ''"
                :hoVaTen="nguoiDungKhachHang.hoVaTen || ''"
            />
        </div>
        <div class="col-10 border-start border-dark-subtle">
            <div class="row justify-content-evenly m-0 mt-3 mb-3 card-header">
                <h5 class="card-title mb-0">
                    Đơn hàng {{ donHang.maDonHang }} >>
                    <a :href="donHang?.tepDinhKem ?? '#'">Tải hợp đồng</a>
                </h5>
            </div>

            <div class="row m-0">
                <div class="text-black fw-bolder">Thông tin khiếu nại</div>
                <div v-if="isError" class="alert alert-danger">
                    {{ messageError }}
                </div>
                <div class="row mt-3">
                    <div class="col-6">
                        <div class="mb-3">
                            <label for="maLoaiKhieuNai" class="form-label"
                                >Loại khiếu nại<span class="text-danger">*</span></label
                            >
                            <select
                                v-model="khieuNai.maLoaiKhieuNai"
                                class="form-select"
                                aria-label="Default select example"
                            >
                                <option selected disabled>Chọn loại khiếu nại</option>
                                <template
                                    v-for="loaiKhieuNai in loaiKhieuNais"
                                    :key="loaiKhieuNai.maLoaiKhieuNai"
                                >
                                    <option :value="loaiKhieuNai.maLoaiKhieuNai">
                                        {{ loaiKhieuNai.tenLoaiKhieuNai }}
                                    </option>
                                </template>
                            </select>
                        </div>
                    </div>
                    <div class="col-6">
                        <div class="mb-3">
                            <label for="anhKhieuNai" class="form-label"
                                >Ảnh khiếu nại<span class="text-danger">*</span></label
                            >
                            <input
                                type="file"
                                name="files"
                                ref="anhKhieuNaiInput"
                                @change="handleAnhKhieuNaiChange"
                                accept="image/*"
                                multiple
                                class="form-control"
                            />
                            <div class="image-preview mt-3 d-flex flex-wrap">
                                <div
                                    v-for="(img, index) in previewAnhKhieuNais"
                                    :key="index"
                                    class="position-relative me-2 mb-2"
                                >
                                    <img
                                        :src="img"
                                        alt="Ảnh xem trước"
                                        width="120px"
                                        height="120px"
                                        class="border rounded"
                                    />
                                    <button
                                        @click="removeAnhKhieuNai(index)"
                                        class="btn btn-danger btn-sm position-absolute top-0 end-0"
                                    >
                                        X
                                    </button>
                                </div>
                            </div>
                            <p v-if="isErrorAnhKhieuNai" class="text-danger">
                                {{ messageAnhKhieuNai }}
                            </p>
                        </div>
                    </div>
                </div>
                <div class="row">
                    <div class="mb-3">
                        <label for="noiDungKhieuNai" class="form-label"
                            >Nội dung khiếu nại<span class="text-danger">*</span></label
                        >
                        <textarea
                            v-model="khieuNai.noiDungKhieuNai"
                            type="text"
                            class="form-control"
                            id="noiDungKhieuNai"
                            rows="3"
                        ></textarea>
                    </div>
                </div>
                <div class="mt-3 mb-3">
                    <div class="text-center">
                        <button
                            type="button"
                            class="btn btn-primary"
                            title="Gửi khiếu nại"
                            @click.prevent="handleKhieuNai"
                            :disabled="isLoading"
                        >
                            Gửi khiếu nại
                        </button>
                    </div>
                </div>
            </div>

            <div class="row m-0">
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
                                <tr
                                    v-for="(item, index) in xeMays"
                                    :key="(item as any).xeMay.maXeMay"
                                >
                                    <td>
                                        {{ index + 1 }}
                                    </td>
                                    <td>
                                        {{ (item as any).xeMay.tenXe }}
                                        <br /><img
                                            :src="(item as any).anhXeMays[0].duongDan"
                                            :alt="(item as any).xeMay.tenXe"
                                            width="180px"
                                            height="120px"
                                        />
                                    </td>
                                    <td>
                                        {{ (item as any).xeMay.bienSoXe }}
                                    </td>
                                    <td>{{ formattedGiaThue((item as any).xeMay.giaThue) }} VNĐ</td>
                                    <td>{{ formatDate(String((item as any).tuNgay)) }}</td>
                                    <td>{{ formatDate(String((item as any).denNgay)) }}</td>
                                </tr>
                            </tbody>
                        </table>
                        <div class="card-price">
                            Tổng tiền: {{ formattedTongTien(tongTien) }} VNĐ
                        </div>
                    </div>
                    <div class="col-4 bg-white p-4">
                        <div class="text-black fw-bolder mb-3 text-center">Thông tin nhân viên</div>
                        <div class="row">
                            <div class="col-4 title-user">Họ và tên:</div>
                            <div class="col-8 content-user">{{ nguoiDungNhanVien.hoVaTen }}</div>
                        </div>
                        <div class="row">
                            <div class="col-4 title-user">Email:</div>
                            <div class="col-8 content-user">{{ nguoiDungNhanVien.email }}</div>
                        </div>
                        <div class="row">
                            <div class="col-4 title-user">Giới tính:</div>
                            <div class="col-8 content-user">{{ nguoiDungNhanVien.gioiTinh }}</div>
                        </div>
                        <div class="row">
                            <div class="col-4 title-user">Ngày sinh:</div>
                            <div class="col-8 content-user">
                                {{
                                    nguoiDungNhanVien.ngaySinh
                                        ? formatDate(String(nguoiDungNhanVien.ngaySinh))
                                        : 'NaN'
                                }}
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-4 title-user">SĐT:</div>
                            <div class="col-8 content-user">
                                {{ nguoiDungNhanVien.soDienThoai }}
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-4 title-user">Số CCCD:</div>
                            <div class="col-8 content-user">{{ nguoiDungNhanVien.soCCCD }}</div>
                        </div>
                    </div>
                </div>

                <div class="row bg-white mt-2 ms-0 pt-3">
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
                                        disabled
                                    />
                                </div>
                            </div>
                            <div class="col-6">
                                <div class="mb-3">
                                    <label for="ngayTraXe" class="form-label"
                                        >Ngày trả xe<span class="text-danger">*</span></label
                                    >
                                    <input
                                        v-model="donHang.ngayTraXe"
                                        type="datetime-local"
                                        class="form-control"
                                        id="ngayTraXe"
                                        disabled
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
                                disabled
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
                </div>
            </div>
        </div>
    </div>
    <PopupLoading :isLoading="isLoading" />
    <PopupLoading :isLoading="isLoadingPage" />
</template>
<script lang="ts">
    import PopupLoading from '@/components/dungchung/PopupLoading.vue'
    import { useDate } from '@/composables/useDate'
    import { getDonHangByMaDonHang } from '@/services/donHangService'
    import { getKhachHangByMaNguoiDung } from '@/services/khachHangService'
    import { addKhieuNai } from '@/services/khieuNaiService'
    import { getLoaiKhieuNais } from '@/services/loaiKhieuNaiService'
    import { getNhanVienByMaNguoiDung, getNhanViensByChuCuaHang } from '@/services/nhanVienService'
    import { getXeMayByMaXeMay } from '@/services/xeMayService'
    import { computed, defineComponent, onMounted, ref } from 'vue'
    import { useRoute, useRouter } from 'vue-router'
    import ThongTinKhachHang from '../dungchung/ThongTinKhachHang.vue'

    export default defineComponent({
        name: 'GuiKhieuNai',
        components: {
            PopupLoading,
            ThongTinKhachHang
        },
        setup() {
            const route = useRoute()
            const router = useRouter()
            const isError = ref(false)
            const messageError = ref<string>('')
            const isErrorAnhKhieuNai = ref(false)
            const messageAnhKhieuNai = ref<string>('')
            const donHang = ref<Record<string, null>>({})
            const nguoiDungKhachHang = ref<Record<string, null>>({})
            const nguoiDungNhanVien = ref<Record<string, null>>({})
            const khachHang = ref<Record<string, null>>({})
            const chuCuaHang = ref<Record<string, null>>({})
            const xeMays = ref<Record<string, string>[]>([])
            const chiTietDonHangs = ref<Record<string, string>[]>([])
            const nhanViens = ref<Record<string, unknown>[]>([])
            const isLoading = ref(false)
            const tenNhanViens = ref<Record<string, string>>({})
            const isLoadingPage = ref(true)
            const khieuNai = ref<Record<string, undefined>>({})
            const previewAnhKhieuNais = ref<string[]>([])
            const selectedAnhKhieuNais = ref<File[]>([])
            const anhKhieuNaiInput = ref<HTMLInputElement | null>(null)
            const loaiKhieuNais = ref<Record<string, unknown>[]>([])

            onMounted(async () => {
                await Promise.all([fetchDonHang(), fetchNhanViens(), fetchLoaiKhieuNais()])
                isLoadingPage.value = false
            })

            const fetchLoaiKhieuNais = async () => {
                const response = await getLoaiKhieuNais()
                loaiKhieuNais.value = response
            }

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

                const responseNhanVien = await getNhanVienByMaNguoiDung(
                    responseDonHang.nhanVien.maNhanVien
                )
                nguoiDungNhanVien.value = responseNhanVien.nguoiDung

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

                getTenNhanVien(String(donHang.value.maNhanVien))
            }

            const fetchNhanViens = async () => {
                const response = await getNhanViensByChuCuaHang(
                    String(chuCuaHang.value.maChuCuaHang)
                )
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

            const getTenNhanVien = async (maNhanVien: string) => {
                if (!tenNhanViens.value[maNhanVien]) {
                    const nhanVien = await getNhanVienByMaNguoiDung(maNhanVien)
                    tenNhanViens.value[maNhanVien] = nhanVien ? nhanVien.nguoiDung.hoVaTen : ''
                }
                return tenNhanViens.value[maNhanVien]
            }

            const handleAnhKhieuNaiChange = (event: Event) => {
                const target = event.target as HTMLInputElement
                const files = target.files
                if (!files?.length) return

                previewAnhKhieuNais.value.forEach((url) => URL.revokeObjectURL(url))
                previewAnhKhieuNais.value = []
                selectedAnhKhieuNais.value = Array.from(files)

                for (const file of selectedAnhKhieuNais.value) {
                    const objectUrl = URL.createObjectURL(file)
                    previewAnhKhieuNais.value.push(objectUrl)
                }
            }

            const removeAnhKhieuNai = (index: number) => {
                URL.revokeObjectURL(previewAnhKhieuNais.value[index])

                previewAnhKhieuNais.value.splice(index, 1)
                selectedAnhKhieuNais.value.splice(index, 1)

                if (previewAnhKhieuNais.value.length === 0 && anhKhieuNaiInput.value) {
                    anhKhieuNaiInput.value.value = ''
                }
            }

            const handleKhieuNai = async () => {
                if (!khieuNai.value.noiDungKhieuNai || !khieuNai.value.maLoaiKhieuNai) {
                    isError.value = true
                    messageError.value = 'Vui lòng nhập đầy đủ các trường dữ liệu!'
                    setTimeout(() => {
                        isError.value = false
                        messageError.value = ''
                    }, 3000)
                    return
                }

                if (previewAnhKhieuNais.value.length === 0) {
                    isErrorAnhKhieuNai.value = true
                    messageAnhKhieuNai.value = 'Vui lòng chọn ảnh!'
                    setTimeout(() => {
                        isError.value = false
                        messageError.value = ''
                    }, 3000)
                    return
                }

                isLoading.value = true
                try {
                    const formData = new FormData()
                    Object.entries(khieuNai.value).forEach(([key, value]) => {
                        formData.append(key, value || '')
                    })
                    formData.append('maDonHang', String(donHang.value.maDonHang))

                    selectedAnhKhieuNais.value.forEach((file) => {
                        formData.append('anhKhieuNaiList', file)
                    })

                    const response = await addKhieuNai(formData)
                    if (response.success) {
                        await router.push({ name: 'TrangChu' })
                    }
                } catch (error) {
                    console.error('Lỗi khi thêm mới: ', error)
                } finally {
                    isLoading.value = false
                }
            }

            return {
                isLoadingPage,
                isErrorAnhKhieuNai,
                messageAnhKhieuNai,
                previewAnhKhieuNais,
                selectedAnhKhieuNais,
                anhKhieuNaiInput,
                khieuNai,
                handleKhieuNai,
                removeAnhKhieuNai,
                tenNhanViens,
                tongTien,
                formatDate,
                formattedGiaThue,
                formattedTongTien,
                handleAnhKhieuNaiChange,
                isError,
                messageError,
                donHang,
                xeMays,
                nhanViens,
                nguoiDungKhachHang,
                nguoiDungNhanVien,
                khachHang,
                isLoading,
                chiTietDonHangs,
                loaiKhieuNais
            }
        }
    })
</script>

<style>
    .leaflet-routing-container {
        display: none !important;
    }

    .bank-button {
        margin-left: 10px;
        margin-top: 10px;
        width: 90%;
        padding: 10px;
        border: 1px solid #ccc;
        border-radius: 5px;
        background-color: #fff;
        cursor: pointer;
        transition: background-color 0.3s;
    }
    .bank-button:hover {
        background-color: #f0f0f0;
    }

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
