<template>
    <section class="py-3 py-md-5 pt-0 bg-white">
        <div class="container">
            <div class="row">
                <div class="col-12">
                    <div class="mb-5">
                        <h2 class="display-5 fw-bold text-center text-uppercase">Đăng ký</h2>
                        <p class="text-center m-0">
                            Bạn đã có tài khoản? <a href="/dang-nhap">Đăng nhập</a>
                        </p>
                    </div>
                </div>
            </div>
            <div class="row m-0">
                <div class="row">
                    <div v-if="isError" class="alert alert-danger">
                        {{ messageError }}
                    </div>
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
                            />
                            <div v-if="isErrorEmail" class="text-danger">
                                {{ messageEmail }}
                            </div>
                        </div>
                        <div class="mb-3">
                            <label for="gioiTinh" class="form-label"
                                >Giới tính<span class="text-danger">*</span></label
                            >
                            <select
                                v-model="nguoiDung.gioiTinh"
                                class="form-select"
                                aria-label="Default select example"
                            >
                                <option selected disabled>Chọn giới tính</option>
                                <option value="Nam">Nam</option>
                                <option value="Nữ">Nữ</option>
                                <option value="Khác">Khác</option>
                            </select>
                        </div>
                        <div class="mb-3">
                            <label for="quanHuyen" class="form-label"
                                >Quận/Huyện<span class="text-danger">*</span></label
                            >
                            <select
                                class="form-select"
                                aria-label="Default select example"
                                v-model="selectedQuanHuyen"
                            >
                                <option selected disabled>Chọn quận/huyện</option>
                                <template
                                    v-for="quanHuyen in quanHuyens"
                                    :key="quanHuyen.maQuanHuyen"
                                >
                                    <option :value="quanHuyen.maQuanHuyen">
                                        {{ quanHuyen.tenQuanHuyen }}
                                    </option>
                                </template>
                            </select>
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
                            />
                            <div v-if="isErrorSoDienThoai" class="text-danger">
                                {{ messageSoDienThoai }}
                            </div>
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
                            />
                        </div>
                        <div class="mb-3">
                            <label for="matKhau" class="form-label"
                                >Mật khẩu<span class="text-danger">*</span></label
                            >
                            <input
                                v-model="nguoiDung.matKhau"
                                type="text"
                                class="form-control"
                                id="matKhau"
                            />
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
                            />
                        </div>
                        <div class="mb-3">
                            <label for="maPhuongXa" class="form-label"
                                >Phường/Xã<span class="text-danger">*</span></label
                            >
                            <select
                                v-model="chuCuaHang.maPhuongXa"
                                class="form-select"
                                aria-label="Default select example"
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
                            <label for="soCCCD" class="form-label"
                                >Số căn cước công dân<span class="text-danger">*</span></label
                            >
                            <input
                                v-model="nguoiDung.soCCCD"
                                type="text"
                                class="form-control"
                                id="soCCCD"
                            />
                            <div v-if="isErrorSoCCCD" class="text-danger">
                                {{ messageSoCCCD }}
                            </div>
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
                                @change="handleFileChange"
                                accept="image/*"
                                class="form-control mt-2"
                            />
                            <p v-if="isErrorAnh" class="text-danger">{{ messageAnh }}</p>
                        </div>
                    </div>
                </div>
                <div class="row">
                    <div class="text-black fw-bolder">Thông tin cửa hàng</div>
                    <div class="col-xl-4">
                        <div class="mb-3">
                            <label for="tenCuaHang" class="form-label"
                                >Tên cửa hàng<span class="text-danger">*</span></label
                            >
                            <input
                                v-model="chuCuaHang.tenCuaHang"
                                type="text"
                                class="form-control"
                                id="tenCuaHang"
                            />
                        </div>
                    </div>
                    <div class="col-xl-4">
                        <div class="mb-3">
                            <label for="diaChiCuaHang" class="form-label"
                                >Địa chỉ cửa hàng<span class="text-danger">*</span></label
                            >
                            <input
                                v-model="chuCuaHang.diaChiCuaHang"
                                type="text"
                                class="form-control"
                                id="diaChiCuaHang"
                            />
                        </div>
                    </div>
                    <div class="col-xl-4">
                        <div class="mb-3">
                            <label for="soDienThoaiCuaHang" class="form-label"
                                >Số điện thoại cửa hàng<span class="text-danger">*</span></label
                            >
                            <input
                                v-model="chuCuaHang.soDienThoaiCuaHang"
                                type="text"
                                class="form-control"
                                id="soDienThoaiCuaHang"
                            />
                            <div v-if="isErrorSoDienThoaiCuaHang" class="text-danger">
                                {{ messageSoDienThoaiCuaHang }}
                            </div>
                        </div>
                    </div>
                </div>
                <div class="row">
                    <div class="col-6 text-center">
                        <a
                            href="/trang-chu"
                            class="btn btn-outline-danger"
                            title="Hủy"
                            :disabled="isLoading"
                        >
                            Hủy
                        </a>
                    </div>
                    <div class="col-6 text-center">
                        <button
                            type="button"
                            class="btn btn-success"
                            title="Đăng ký"
                            @click.prevent="handleDangKy"
                            :disabled="isLoading"
                        >
                            Đăng ký
                        </button>
                    </div>
                </div>
            </div>
        </div>
    </section>
    <PopupLoading :isLoading="isLoading" />
</template>

<script lang="ts">
    import {
        dangKyChuCuaHang,
        getPhuongXasByMaQuanHuyen,
        getQuanHuyens
    } from '@/services/authService'
    import { validateEmail, validateSoCCCD, validateSoDienThoai } from '@/utils/validation'
    import { defineComponent, onMounted, ref, watch } from 'vue'
    import { useRoute, useRouter } from 'vue-router'
    import PopupLoading from '../dungchung/PopupLoading.vue'
    export default defineComponent({
        name: 'DangKyChuCuaHang',
        components: {
            PopupLoading
        },
        setup() {
            const route = useRoute()
            const router = useRouter()
            const fileInput = ref<HTMLInputElement | null>(null)
            const isError = ref(false)
            const messageError = ref<string>('')
            const nguoiDung = ref<Record<string, undefined>>({})
            const chuCuaHang = ref<Record<string, undefined>>({})
            const quanHuyens = ref<Array<{ maQuanHuyen: string; tenQuanHuyen: string }>>([])
            const phuongXas = ref<Array<{ maPhuongXa: string; tenPhuongXa: string }>>([])
            const selectedQuanHuyen = ref<string | null>(null)
            const maVaiTroPrams = String(route.params.maVaiTro)
            const isErrorEmail = ref(false)
            const messageEmail = ref<string>('')
            const isErrorSoDienThoai = ref(false)
            const messageSoDienThoai = ref<string>('')
            const isErrorSoCCCD = ref(false)
            const messageSoCCCD = ref<string>('')
            const isErrorSoDienThoaiCuaHang = ref(false)
            const messageSoDienThoaiCuaHang = ref<string>('')
            const isErrorAnh = ref(false)
            const messageAnh = ref<string>('')
            const previewImage = ref<string | null>(null)
            const isLoading = ref(false)

            const fetchQuanHuyens = async () => {
                const result = await getQuanHuyens()
                quanHuyens.value = result
            }

            const fetchPhuongXas = async (maQuanHuyen: string) => {
                const result = await getPhuongXasByMaQuanHuyen(maQuanHuyen)
                if (result) {
                    phuongXas.value = result
                } else {
                    phuongXas.value = []
                }
            }

            watch(selectedQuanHuyen, (newMaQuanHuyen) => {
                if (newMaQuanHuyen) {
                    fetchPhuongXas(String(newMaQuanHuyen))
                } else {
                    phuongXas.value = []
                }
            })

            onMounted(() => {
                fetchQuanHuyens()
            })

            const handleFileChange = (event: Event) => {
                const target = event.target as HTMLInputElement
                const file = target.files?.[0]

                if (file) {
                    previewImage.value = URL.createObjectURL(file)
                    isError.value = false
                } else {
                    previewImage.value = null
                    isError.value = true
                }
            }

            const handleDangKy = async () => {
                const file = fileInput.value?.files?.[0]
                let hasError = false

                if (
                    !nguoiDung.value.hoVaTen ||
                    !nguoiDung.value.email ||
                    !nguoiDung.value.matKhau ||
                    !nguoiDung.value.gioiTinh ||
                    !nguoiDung.value.diaChi ||
                    !nguoiDung.value.soCCCD ||
                    !selectedQuanHuyen.value ||
                    !chuCuaHang.value.maPhuongXa ||
                    !nguoiDung.value.soDienThoai ||
                    !nguoiDung.value.ngaySinh ||
                    !chuCuaHang.value.tenCuaHang ||
                    !chuCuaHang.value.diaChiCuaHang ||
                    !chuCuaHang.value.soDienThoaiCuaHang
                ) {
                    isError.value = true
                    messageError.value = 'Vui lòng nhập đầy đủ các trường dữ liệu!'
                    setTimeout(() => {
                        isError.value = false
                        messageError.value = ''
                    }, 3000)
                    return
                }

                if (!file) {
                    isErrorAnh.value = true
                    messageAnh.value = 'Vui lòng chọn ảnh!'
                    hasError = true
                    return
                }

                const soCCCDCheck = validateSoCCCD(String(nguoiDung.value.soCCCD))
                if (!soCCCDCheck.isValid) {
                    isErrorSoCCCD.value = true
                    messageSoCCCD.value = soCCCDCheck.message || ''
                    hasError = true
                }

                const soDienThoaiCheck = validateSoDienThoai(String(nguoiDung.value.soDienThoai))
                if (!soDienThoaiCheck.isValid) {
                    isErrorSoDienThoai.value = true
                    messageSoDienThoai.value = soDienThoaiCheck.message || ''
                    hasError = true
                }

                const soDienThoaiCuaHangCheck = validateSoDienThoai(
                    String(chuCuaHang.value.soDienThoaiCuaHang)
                )
                if (!soDienThoaiCuaHangCheck.isValid) {
                    isErrorSoDienThoaiCuaHang.value = true
                    messageSoDienThoaiCuaHang.value = soDienThoaiCuaHangCheck.message || ''
                    hasError = true
                }

                const emailCheck = validateEmail(String(nguoiDung.value.email))
                if (!emailCheck.isValid) {
                    isErrorEmail.value = true
                    messageEmail.value = emailCheck.message || ''
                    hasError = true
                }

                if (hasError) {
                    setTimeout(() => {
                        isErrorAnh.value = false
                        messageAnh.value = ''
                        isErrorSoDienThoai.value = false
                        messageSoDienThoai.value = ''
                        isErrorSoDienThoaiCuaHang.value = false
                        messageSoDienThoaiCuaHang.value = ''
                        isErrorEmail.value = false
                        messageEmail.value = ''
                        isErrorSoCCCD.value = false
                        messageSoCCCD.value = ''
                    }, 3000)
                    return
                }

                isLoading.value = true
                try {
                    const formData = new FormData()
                    Object.entries(nguoiDung.value).forEach(([key, value]) => {
                        if (value !== undefined) {
                            formData.append(key, value || '')
                        }
                    })
                    Object.entries(chuCuaHang.value).forEach(([key, value]) => {
                        if (value !== undefined) {
                            formData.append(key, value || '')
                        }
                    })
                    formData.append('file', file)

                    const response = await dangKyChuCuaHang(formData)
                    if (response.success) {
                        isLoading.value = false
                        window.alert('Đăng ký tài khoản thành công!')
                        await router.push({ name: 'DangNhap' })
                    }
                } catch (error) {
                    console.error('Lỗi khi thêm mới: ', error)
                    isLoading.value = false
                } finally {
                    isLoading.value = false
                }
            }

            return {
                fileInput,
                isError,
                messageError,
                isErrorEmail,
                messageEmail,
                isErrorSoDienThoai,
                messageSoDienThoai,
                isErrorSoDienThoaiCuaHang,
                messageSoDienThoaiCuaHang,
                isErrorAnh,
                messageAnh,
                selectedQuanHuyen,
                nguoiDung,
                chuCuaHang,
                quanHuyens,
                phuongXas,
                previewImage,
                handleFileChange,
                handleDangKy,
                maVaiTroPrams,
                isLoading,
                isErrorSoCCCD,
                messageSoCCCD
            }
        }
    })
</script>
