<template>
    <div class="container-fluid">
        <div class="row justify-content-center">
            <div class="btn-group">
                <router-link
                    :to="{
                        name: 'DanhSachNguoiDungView',
                        params: { maVaiTro: 'VT2' }
                    }"
                    class="btn btn-primary text-uppercase border-end"
                    :class="{ active: maVaiTroPrams === 'VT2' }"
                    aria-current="page"
                    >Chủ cửa hàng</router-link
                >
                <router-link
                    :to="{
                        name: 'DanhSachNguoiDungView',
                        params: { maVaiTro: 'VT3' }
                    }"
                    class="btn btn-primary text-uppercase border-start border-end"
                    :class="{ active: maVaiTroPrams === 'VT3' }"
                    >Nhân viên</router-link
                >
                <router-link
                    :to="{
                        name: 'DanhSachNguoiDungView',
                        params: { maVaiTro: 'VT4' }
                    }"
                    class="btn btn-primary text-uppercase border-start border-end"
                    :class="{ active: maVaiTroPrams === 'VT4' }"
                    >Khách hàng</router-link
                >
            </div>
        </div>
        <div class="row justify-content-evenly m-0 mt-3 mb-3">
            <div class="card-header col-xl-6">
                <h5 class="card-title mb-0">Cập nhật khách hàng</h5>
            </div>
            <div class="card-header col-xl-6 text-end">
                <router-link
                    class="text-success"
                    :to="{ name: 'DanhSachNguoiDungView', params: { maVaiTro: maVaiTroPrams } }"
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
                        <label for="trangThaiHoatDong" class="form-label"
                            >Trạng thái hoạt động<span class="text-danger">*</span></label
                        >
                        <select
                            class="form-select"
                            aria-label="Default select example"
                            v-model="nguoiDung.trangThaiHoatDong"
                        >
                            <option selected disabled>Chọn tình trạng</option>
                            <option value="Hoạt động">Hoạt động</option>
                            <option value="Không hoạt động">Không hoạt động</option>
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
                            @change="handleFileChange"
                            accept="image/*"
                            class="form-control mt-2"
                        />
                        <p v-if="isErrorAnh" class="text-danger">{{ messageAnh }}</p>
                    </div>
                </div>
            </div>
            <div class="row">
                <div class="col-xl-8">
                    <div class="row">
                        <div class="col-xl-4">
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
                        <div class="col-xl-4">
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
                    </div>
                </div>
            </div>
            <div class="text-center">
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
</template>

<script lang="ts">
    import PopupLoading from '@/components/dungchung/PopupLoading.vue'
    import { getKhachHangByMaNguoiDung, updateKhachHangByAdmin } from '@/services/khachHangService'
    import {
        validateEmail,
        validateSoCCCD,
        validateSoDienThoai,
        validateSoGPLX
    } from '@/utils/validation'
    import { defineComponent, onMounted, ref } from 'vue'
    import { useRoute, useRouter } from 'vue-router'

    export default defineComponent({
        name: 'CapNhatKhachHang',
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
            const khachHang = ref<Record<string, undefined>>({})
            const maVaiTroPrams = String(route.params.maVaiTro)
            const isErrorEmail = ref(false)
            const messageEmail = ref<string>('')
            const isErrorSoDienThoai = ref(false)
            const messageSoDienThoai = ref<string>('')
            const isErrorSoCCCD = ref(false)
            const messageSoCCCD = ref<string>('')
            const isErrorSoGPLX = ref(false)
            const messageSoGPLX = ref<string>('')
            const isErrorAnh = ref(false)
            const messageAnh = ref<string>('')
            const previewImage = ref<string | null>(null)
            const isLoading = ref(false)

            const fetchNguoiDung = async () => {
                const maNguoiDung = String(route.params.maNguoiDung)
                const response = await getKhachHangByMaNguoiDung(maNguoiDung)
                nguoiDung.value = response.nguoiDung
                khachHang.value = response.khachHang
                previewImage.value =
                    nguoiDung.value.anhDaiDien ||
                    'https://res.cloudinary.com/springboot-cloud/image/upload/v1739427632/user_vqmka8.png'
            }

            onMounted(() => {
                fetchNguoiDung()
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

            const handleCapNhat = async () => {
                const file = fileInput.value?.files?.[0]
                let hasError = false

                if (
                    !nguoiDung.value.hoVaTen ||
                    !nguoiDung.value.email ||
                    !nguoiDung.value.trangThaiHoatDong ||
                    !nguoiDung.value.gioiTinh ||
                    !nguoiDung.value.diaChi ||
                    !nguoiDung.value.soDienThoai ||
                    !nguoiDung.value.ngaySinh ||
                    !nguoiDung.value.soCCCD ||
                    !khachHang.value.soGPLX
                ) {
                    isError.value = true
                    messageError.value = 'Vui lòng nhập đầy đủ các trường dữ liệu!'
                    setTimeout(() => {
                        isError.value = false
                        messageError.value = ''
                    }, 3000)
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

                const soGPLXCheck = validateSoGPLX(String(khachHang.value.soGPLX))
                if (!soGPLXCheck.isValid) {
                    isErrorSoGPLX.value = true
                    messageSoGPLX.value = soGPLXCheck.message || ''
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
                    Object.entries(nguoiDung.value).forEach(([key, value]) => {
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
                    if (file) {
                        formData.append('file', file)
                    }

                    const response = await updateKhachHangByAdmin(
                        String(nguoiDung.value.maNguoiDung),
                        formData
                    )
                    if (response.success) {
                        await router.push({
                            name: 'DanhSachNguoiDungView',
                            params: { maVaiTro: maVaiTroPrams }
                        })
                    }
                } catch (error) {
                    console.error('Lỗi khi cập nhật: ', error)
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
                isErrorSoGPLX,
                messageSoGPLX,
                isErrorSoCCCD,
                messageSoCCCD,
                isErrorAnh,
                messageAnh,
                nguoiDung,
                khachHang,
                previewImage,
                handleFileChange,
                handleCapNhat,
                maVaiTroPrams,
                isLoading
            }
        }
    })
</script>
