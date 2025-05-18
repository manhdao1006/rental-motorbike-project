<template>
    <div class="container-fluid">
        <div class="row justify-content-evenly m-0 mt-3 mb-3">
            <div class="card-header col-xl-6">
                <h5 class="card-title mb-0">Chi tiết cửa hàng</h5>
            </div>
            <div class="card-header col-xl-6 text-end">
                <router-link class="text-success" :to="{ name: 'DanhSachCuaHangView' }">
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
                <div class="col-xl-4">
                    <div class="mb-3">
                        <label for="trangThaiHoatDong" class="form-label"
                            >Trạng thái hoạt động<span class="text-danger">*</span></label
                        >
                        <select
                            class="form-select"
                            aria-label="Default select example"
                            v-model="nguoiDung.trangThaiHoatDong"
                        >
                            <option selected disabled>Chọn tình trạng</option>
                            <option value="Chờ duyệt">Chờ duyệt</option>
                            <option value="Hoạt động">Duyệt</option>
                            <option value="Từ chối">Từ chối</option>
                        </select>
                    </div>
                </div>
                <div class="col-xl-4">
                    <div class="mb-3" v-if="nguoiDung.trangThaiHoatDong === 'Từ chối'">
                        <label for="lyDoTuChoi" class="form-label"
                            >Lý do từ chối<span class="text-danger">*</span></label
                        >
                        <input
                            v-model="chuCuaHang.lyDoTuChoi"
                            type="text"
                            class="form-control"
                            id="lyDoTuChoi"
                        />
                    </div>
                </div>
            </div>
            <div class="row">
                <div class="text-black fw-bolder">Thông tin chủ cửa hàng</div>
                <div class="col-xl-4">
                    <div class="mb-3">
                        <label for="hoVaTen" class="form-label"
                            >Họ và tên<span class="text-danger">*</span></label
                        >
                        <input
                            v-model="nguoiDung.hoVaTen"
                            type="text"
                            class="form-control"
                            id="hoVaTen"
                            disabled
                        />
                    </div>
                    <div class="mb-3">
                        <label for="email" class="form-label"
                            >Email<span class="text-danger">*</span></label
                        >
                        <input
                            v-model="nguoiDung.email"
                            type="email"
                            class="form-control"
                            id="email"
                            disabled
                        />
                    </div>
                    <div class="mb-3">
                        <label for="gioiTinh" class="form-label"
                            >Giới tính<span class="text-danger">*</span></label
                        >
                        <select
                            v-model="nguoiDung.gioiTinh"
                            class="form-select"
                            aria-label="Default select example"
                            disabled
                        >
                            <option selected disabled>Chọn giới tính</option>
                            <option value="Nam">Nam</option>
                            <option value="Nữ">Nữ</option>
                            <option value="Khác">Khác</option>
                        </select>
                    </div>
                    <div class="mb-3">
                        <label for="soCCCD" class="form-label"
                            >Số căn cước công dân<span class="text-danger">*</span></label
                        >
                        <input
                            v-model="nguoiDung.soCCCD"
                            type="text"
                            class="form-control"
                            id="soCCCD"
                            disabled
                        />
                    </div>
                    <div class="mb-3">
                        <label for="soDienThoai" class="form-label"
                            >Số điện thoại<span class="text-danger">*</span></label
                        >
                        <input
                            v-model="nguoiDung.soDienThoai"
                            type="text"
                            class="form-control"
                            id="soDienThoai"
                            disabled
                        />
                    </div>
                </div>
                <div class="col-xl-4">
                    <div class="mb-3">
                        <label for="ngaySinh" class="form-label"
                            >Ngày sinh<span class="text-danger">*</span></label
                        >
                        <input
                            v-model="nguoiDung.ngaySinh"
                            type="date"
                            class="form-control"
                            id="ngaySinh"
                            disabled
                        />
                    </div>
                    <div class="mb-3">
                        <label for="quanHuyen" class="form-label"
                            >Quận/Huyện<span class="text-danger">*</span></label
                        >
                        <select
                            class="form-select"
                            aria-label="Default select example"
                            v-model="selectedQuanHuyen"
                            disabled
                        >
                            <option selected disabled>Chọn quận/huyện</option>
                            <template v-for="quanHuyen in quanHuyens" :key="quanHuyen.maQuanHuyen">
                                <option :value="quanHuyen.maQuanHuyen">
                                    {{ quanHuyen.tenQuanHuyen }}
                                </option>
                            </template>
                        </select>
                    </div>
                    <div class="mb-3">
                        <label for="maPhuongXa" class="form-label"
                            >Phường/Xã<span class="text-danger">*</span></label
                        >
                        <select
                            v-model="chuCuaHang.maPhuongXa"
                            class="form-select"
                            aria-label="Default select example"
                            disabled
                        >
                            <option selected disabled>Chọn phường/xã</option>
                            <template v-for="phuongXa in phuongXas" :key="phuongXa.maPhuongXa">
                                <option :value="phuongXa.maPhuongXa">
                                    {{ phuongXa.tenPhuongXa }}
                                </option>
                            </template>
                        </select>
                    </div>
                    <div class="mb-3">
                        <label for="diaChi" class="form-label"
                            >Địa chỉ<span class="text-danger">*</span></label
                        >
                        <input
                            v-model="nguoiDung.diaChi"
                            type="text"
                            class="form-control"
                            id="diaChi"
                            disabled
                        />
                    </div>
                    <div class="mb-3">
                        <label for="soDienThoaiCuaHang" class="form-label"
                            >Số điện thoại cửa hàng<span class="text-danger">*</span></label
                        >
                        <input
                            v-model="chuCuaHang.soDienThoaiCuaHang"
                            type="text"
                            class="form-control"
                            id="soDienThoaiCuaHang"
                            disabled
                        />
                    </div>
                </div>
                <div class="col-xl-4">
                    <div class="profile-img text-center">
                        <img
                            :src="
                                previewImage ||
                                'https://res.cloudinary.com/springboot-cloud/image/upload/v1739427632/user_vqmka8.png'
                            "
                            alt=""
                            width="240px"
                            height="240px"
                        />
                        <input
                            type="file"
                            name="file"
                            ref="fileInput"
                            accept="image/*"
                            class="form-control mt-2"
                            disabled
                        />
                    </div>
                </div>
            </div>
            <div class="row">
                <div class="text-black fw-bolder">Thông tin cửa hàng</div>
                <div class="col-xl-6">
                    <div class="mb-3">
                        <label for="tenCuaHang" class="form-label"
                            >Tên cửa hàng<span class="text-danger">*</span></label
                        >
                        <input
                            v-model="chuCuaHang.tenCuaHang"
                            type="text"
                            class="form-control"
                            id="tenCuaHang"
                            disabled
                        />
                    </div>
                </div>
                <div class="col-xl-6">
                    <div class="mb-3">
                        <label for="diaChiCuaHang" class="form-label"
                            >Địa chỉ cửa hàng<span class="text-danger">*</span></label
                        >
                        <input
                            v-model="chuCuaHang.diaChiCuaHang"
                            type="text"
                            class="form-control"
                            id="diaChiCuaHang"
                            disabled
                        />
                    </div>
                </div>
            </div>
            <div class="text-center mt-3">
                <button
                    type="button"
                    class="btn btn-success"
                    title="Cập nhật"
                    @click.prevent="handleCapNhat"
                    :disabled="isLoading"
                >
                    Cập nhật
                </button>
            </div>
        </div>
    </div>
    <PopupLoading :isLoading="isLoading" />
    <PopupLoading :isLoading="isLoadingPage" />
</template>
<script lang="ts">
    import PopupLoading from '@/components/dungchung/PopupLoading.vue'
    import { getQuanHuyenByMaPhuongXa, getQuanHuyens, sendEmail } from '@/services/authService'
    import { getChuCuaHangByMaNguoiDung, updateChuCuaHang } from '@/services/chuCuaHangService'
    import { defineComponent, onMounted, ref } from 'vue'
    import { useRoute, useRouter } from 'vue-router'

    export default defineComponent({
        name: 'ChiTietCuaHang',
        components: {
            PopupLoading
        },
        setup() {
            const route = useRoute()
            const router = useRouter()
            const isError = ref(false)
            const messageError = ref<string>('')
            const nguoiDung = ref<Record<string, null>>({})
            const chuCuaHang = ref<Record<string, null>>({})
            const previewImage = ref<string | null>(null)
            const isLoading = ref(false)
            const isLoadingPage = ref(true)
            const quanHuyens = ref<Array<{ maQuanHuyen: string; tenQuanHuyen: string }>>([])
            const phuongXas = ref<
                Array<{ maPhuongXa: string; tenPhuongXa: string; maQuanHuyen: string }>
            >([])
            const selectedQuanHuyen = ref<string | null | undefined>(null)

            const fetchChuCuaHang = async () => {
                const response = await getChuCuaHangByMaNguoiDung(String(route.params.maChuCuaHang))
                nguoiDung.value = response.nguoiDung
                chuCuaHang.value = response.chuCuaHang
                previewImage.value =
                    nguoiDung.value.anhDaiDien ||
                    'https://res.cloudinary.com/springboot-cloud/image/upload/v1739427632/user_vqmka8.png'

                if (chuCuaHang.value.maPhuongXa) {
                    await fetchPhuongXas(chuCuaHang.value.maPhuongXa)
                }
            }

            const fetchPhuongXas = async (maPhuongXa: string) => {
                const result = await getQuanHuyenByMaPhuongXa(maPhuongXa)
                phuongXas.value = result.phuongXas
                selectedQuanHuyen.value = result.maQuanHuyen
            }

            const fetchQuanHuyens = async () => {
                const result = await getQuanHuyens()
                quanHuyens.value = result
            }

            onMounted(async () => {
                await Promise.all([fetchChuCuaHang(), fetchQuanHuyens()])
                isLoadingPage.value = false
            })

            const handleCapNhat = async () => {
                if (!nguoiDung.value.trangThaiHoatDong) {
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
                    Object.entries(nguoiDung.value).forEach(([key, value]) => {
                        if (
                            key !== 'matKhau' &&
                            key !== 'vaiTros' &&
                            key !== 'maKhachHang' &&
                            key !== 'maNhanVien' &&
                            key !== 'email' &&
                            key !== 'trangThaiXoa' &&
                            value !== undefined
                        ) {
                            formData.append(key, value || '')
                        }
                    })
                    Object.entries(chuCuaHang.value).forEach(([key, value]) => {
                        if (
                            key !== 'nhanViens' &&
                            key !== 'xeMays' &&
                            key !== 'maNguoiDung' &&
                            key !== 'trangThaiXoa' &&
                            value !== undefined
                        ) {
                            formData.append(key, value || '')
                        }
                    })

                    const response = await updateChuCuaHang(
                        String(chuCuaHang.value.maChuCuaHang),
                        formData
                    )
                    if (response.success) {
                        await sendEmail({
                            email: nguoiDung.value.email ?? '',
                            noiDung:
                                nguoiDung.value.trangThaiHoatDong === 'Từ chối'
                                    ? `Kết quả đăng ký tài khoản cửa hàng: ${nguoiDung.value.trangThaiHoatDong}\nLý do từ chối: ${chuCuaHang.value.lyDoTuChoi}`
                                    : `Kết quả đăng ký tài khoản cửa hàng: Duyệt, được phép hoạt động`,
                            tieuDe: 'Thông báo kết quả đăng ký tài khoản cửa hàng'
                        })

                        await router.push({ name: 'DanhSachCuaHangView' })
                    }
                } catch (error) {
                    console.error('Lỗi khi cập nhật: ', error)
                } finally {
                    isLoading.value = false
                }
            }

            return {
                isLoadingPage,
                isError,
                messageError,
                nguoiDung,
                chuCuaHang,
                handleCapNhat,
                isLoading,
                phuongXas,
                selectedQuanHuyen,
                quanHuyens,
                previewImage
            }
        }
    })
</script>
