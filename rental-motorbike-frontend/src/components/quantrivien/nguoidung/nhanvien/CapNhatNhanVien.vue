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
                    class="btn btn-primary text-uppercase border-start"
                    :class="{ active: maVaiTroPrams === 'VT4' }"
                    >Khách hàng</router-link
                >
            </div>
        </div>
        <div class="row justify-content-evenly m-0 mt-3 mb-3">
            <div class="card-header col-xl-6">
                <h5 class="card-title mb-0">Cập nhật nhân viên</h5>
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
                    <input v-model="nguoiDung.email" type="email" class="form-control" id="email" />
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
    <PopupLoading :isLoading="isLoadingPage" />
</template>

<script lang="ts">
    import PopupLoading from '@/components/dungchung/PopupLoading.vue'

    import { getNhanVienByMaNguoiDung, updateNhanVien } from '@/services/nhanVienService'
    import { validateEmail, validateSoCCCD, validateSoDienThoai } from '@/utils/validation'
    import { defineComponent, onMounted, ref } from 'vue'
    import { useRoute, useRouter } from 'vue-router'

    export default defineComponent({
        name: 'CapNhatNhanVien',
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
            const nhanVien = ref<Record<string, undefined>>({})
            const maVaiTroPrams = String(route.params.maVaiTro)
            const isErrorEmail = ref(false)
            const messageEmail = ref<string>('')
            const isErrorSoDienThoai = ref(false)
            const messageSoDienThoai = ref<string>('')
            const isErrorSoCCCD = ref(false)
            const messageSoCCCD = ref<string>('')
            const isErrorAnh = ref(false)
            const messageAnh = ref<string>('')
            const previewImage = ref<string | null>(null)
            const isLoading = ref(false)
            const isLoadingPage = ref(true)

            const fetchNguoiDung = async () => {
                const maNguoiDung = String(route.params.maNguoiDung)
                const response = await getNhanVienByMaNguoiDung(maNguoiDung)
                nguoiDung.value = response.nguoiDung
                nhanVien.value = response.nhanVien
                previewImage.value =
                    nguoiDung.value.avatar ||
                    'https://res.cloudinary.com/springboot-cloud/image/upload/v1739427632/user_vqmka8.png'
            }

            onMounted(async () => {
                await Promise.all([fetchNguoiDung()])
                isLoadingPage.value = false
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
                    !nguoiDung.value.soCCCD
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
                            value !== undefined
                        ) {
                            formData.append(key, value || '')
                        }
                    })
                    Object.entries(nhanVien.value).forEach(([key, value]) => {
                        if (key !== 'trangThaiXoa' && key !== 'donHangs' && value !== undefined) {
                            formData.append(key, value || '')
                        }
                    })
                    if (file) {
                        formData.append('file', file)
                    }

                    const response = await updateNhanVien(
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
                isLoadingPage,
                isLoading,
                isErrorSoCCCD,
                messageSoCCCD,
                fileInput,
                isError,
                messageError,
                isErrorEmail,
                messageEmail,
                isErrorSoDienThoai,
                messageSoDienThoai,
                isErrorAnh,
                messageAnh,
                nguoiDung,
                nhanVien,
                previewImage,
                handleFileChange,
                handleCapNhat,
                maVaiTroPrams
            }
        }
    })
</script>
