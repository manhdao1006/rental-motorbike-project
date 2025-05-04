<template>
    <div class="container-fluid">
        <div class="row justify-content-evenly m-0 mt-3 mb-3">
            <div class="card-header col-xl-6">
                <h5 class="card-title mb-0">Cập nhật xe máy</h5>
            </div>
            <div class="card-header col-xl-6 text-end">
                <router-link class="text-success" :to="{ name: 'DanhSachXeMayView' }">
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
                <div class="col-xl-6">
                    <div
                        class="mb-3"
                        v-if="
                            xeMay.trangThaiHoatDong === 'Có sẵn' ||
                            xeMay.trangThaiHoatDong === 'Bảo dưỡng' ||
                            xeMay.trangThaiHoatDong === 'Đang cho thuê'
                        "
                    >
                        <label for="trangThaiHoatDong" class="form-label"
                            >Trạng thái hoạt động<span class="text-danger">*</span></label
                        >
                        <select
                            class="form-select"
                            aria-label="Default select example"
                            v-model="xeMay.trangThaiHoatDong"
                        >
                            <option selected disabled>Chọn tình trạng</option>
                            <option value="Có sẵn">Có sẵn</option>
                            <option value="Bảo dưỡng">Bảo dưỡng</option>
                            <option value="Đang cho thuê">Đang cho thuê</option>
                        </select>
                    </div>
                </div>
            </div>
            <div class="col-xl-6">
                <div class="mb-3">
                    <label for="bienSoXe" class="form-label"
                        >Biển số xe máy<span class="text-danger">*</span></label
                    >
                    <input
                        v-model="xeMay.bienSoXe"
                        type="text"
                        class="form-control"
                        id="bienSoXe"
                    />
                </div>
                <div class="row mb-3">
                    <div class="col-xl-6">
                        <label for="maDanhMucXe" class="form-label"
                            >Danh mục xe<span class="text-danger">*</span></label
                        >
                        <select
                            v-model="xeMay.maDanhMucXe"
                            class="form-select"
                            aria-label="Default select example"
                        >
                            <option selected disabled>Chọn danh mục xe</option>
                            <template v-for="danhMucXe in danhMucXes" :key="danhMucXe.maDanhMucXe">
                                <option :value="danhMucXe.maDanhMucXe">
                                    {{ danhMucXe.tenDanhMucXe }}
                                </option>
                            </template>
                        </select>
                    </div>
                    <div class="col-xl-6">
                        <label for="loaiXe" class="form-label"
                            >Loại xe máy<span class="text-danger">*</span></label
                        >
                        <select
                            class="form-select"
                            aria-label="Default select example"
                            v-model="xeMay.loaiXe"
                        >
                            <option selected disabled>Chọn loại xe máy</option>
                            <option value="Xe số">Xe số</option>
                            <option value="Xe tay ga">Xe tay ga</option>
                            <option value="Xe côn tay">Xe côn tay</option>
                        </select>
                    </div>
                </div>
                <div class="mb-3">
                    <label for="soKhung" class="form-label"
                        >Số khung<span class="text-danger">*</span></label
                    >
                    <input v-model="xeMay.soKhung" type="text" class="form-control" id="soKhung" />
                </div>
                <div class="mb-3">
                    <label for="anhXeMay" class="form-label"
                        >Ảnh xe máy<span class="text-danger">*</span></label
                    >
                    <input
                        type="file"
                        name="files"
                        ref="anhXeMayInput"
                        @change="handleAnhXeMayChange"
                        accept="image/*"
                        multiple
                        class="form-control mt-2"
                    />
                    <div class="image-preview mt-3 d-flex flex-wrap">
                        <div
                            v-for="(img, index) in previewAnhXeMays"
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
                                @click="removeAnhXeMay(index)"
                                class="btn btn-danger btn-sm position-absolute top-0 end-0"
                            >
                                X
                            </button>
                        </div>
                    </div>
                    <p v-if="isErrorAnhXeMay" class="text-danger">{{ messageAnhXeMay }}</p>
                </div>
            </div>
            <div class="col-xl-6">
                <div class="mb-3">
                    <label for="tenXe" class="form-label"
                        >Tên xe máy<span class="text-danger">*</span></label
                    >
                    <input v-model="xeMay.tenXe" type="text" class="form-control" id="tenXe" />
                </div>
                <div class="row mb-3">
                    <div class="col-xl-6">
                        <label for="mauXe" class="form-label"
                            >Màu xe<span class="text-danger">*</span></label
                        >
                        <input v-model="xeMay.mauXe" type="text" class="form-control" id="mauXe" />
                    </div>
                    <div class="col-xl-6">
                        <label for="giaThue" class="form-label"
                            >Giá thuê<span class="text-danger">*</span></label
                        >
                        <input
                            v-model="xeMay.giaThue"
                            type="text"
                            class="form-control"
                            id="giaThue"
                        />
                        <div v-if="isErrorGiaThue" class="text-danger">
                            {{ messageGiaThue }}
                        </div>
                    </div>
                </div>
                <div class="mb-3">
                    <label for="soMay" class="form-label"
                        >Số máy<span class="text-danger">*</span></label
                    >
                    <input v-model="xeMay.soMay" type="text" class="form-control" id="soMay" />
                </div>
            </div>
            <div class="row"></div>
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
</template>
<script lang="ts">
    import PopupLoading from '@/components/dungchung/PopupLoading.vue'
    import { getDanhMucXes } from '@/services/danhMucXeService'
    import { getXeMayByMaXeMayByChuCuaHang, updateXeMay } from '@/services/xeMayService'
    import { validateChuSo } from '@/utils/validation'
    import { defineComponent, onMounted, ref } from 'vue'
    import { useRoute, useRouter } from 'vue-router'

    export default defineComponent({
        name: 'CapNhatXeMay',
        components: {
            PopupLoading
        },
        setup() {
            const route = useRoute()
            const router = useRouter()
            const isError = ref(false)
            const messageError = ref<string>('')
            const isErrorAnhXeMay = ref(false)
            const messageAnhXeMay = ref<string>('')
            const isErrorGiaThue = ref(false)
            const messageGiaThue = ref<string>('')
            const xeMay = ref<Record<string, undefined>>({})
            const previewAnhXeMays = ref<string[]>([])
            const selectedAnhXeMays = ref<File[]>([])
            const oldAnhXeMays = ref<string[]>([])
            const deletedAnhXeMays = ref<string[]>([])
            const anhXeMayInput = ref<HTMLInputElement | null>(null)
            const danhMucXes = ref<Record<string, unknown>[]>([])
            const isLoading = ref(false)

            const fetchDanhMucXes = async () => {
                const response = await getDanhMucXes()
                danhMucXes.value = response
            }

            const fetchXeMay = async () => {
                const response = await getXeMayByMaXeMayByChuCuaHang(String(route.params.maXeMay))
                xeMay.value = response.xeMay

                oldAnhXeMays.value = response.anhXeMays.map(
                    (img: { duongDan: string }) => img.duongDan
                )
                previewAnhXeMays.value = [...oldAnhXeMays.value]
            }

            onMounted(() => {
                fetchXeMay()
                fetchDanhMucXes()
            })

            const handleAnhXeMayChange = (event: Event) => {
                const target = event.target as HTMLInputElement
                const files = target.files
                if (!files?.length) return

                const newFiles = Array.from(files)
                selectedAnhXeMays.value.push(...newFiles)

                for (const file of newFiles) {
                    const objectUrl = URL.createObjectURL(file)
                    previewAnhXeMays.value.push(objectUrl)
                }
            }

            const removeAnhXeMay = (index: number) => {
                const imageUrl = previewAnhXeMays.value[index]

                if (oldAnhXeMays.value.includes(imageUrl)) {
                    oldAnhXeMays.value.splice(oldAnhXeMays.value.indexOf(imageUrl), 1)
                    deletedAnhXeMays.value.push(imageUrl)
                } else {
                    URL.revokeObjectURL(imageUrl)
                    selectedAnhXeMays.value.splice(index - oldAnhXeMays.value.length, 1)
                }
                previewAnhXeMays.value.splice(index, 1)

                if (previewAnhXeMays.value.length === 0 && anhXeMayInput.value) {
                    anhXeMayInput.value.value = ''
                }
            }

            const handleCapNhat = async () => {
                let hasError = false

                if (
                    !xeMay.value.bienSoXe ||
                    !xeMay.value.tenXe ||
                    !xeMay.value.mauXe ||
                    !xeMay.value.giaThue ||
                    !xeMay.value.soKhung ||
                    !xeMay.value.soMay ||
                    !xeMay.value.loaiXe ||
                    !xeMay.value.maDanhMucXe
                ) {
                    isError.value = true
                    messageError.value = 'Vui lòng nhập đầy đủ các trường dữ liệu!'
                    setTimeout(() => {
                        isError.value = false
                        messageError.value = ''
                    }, 3000)
                    return
                }

                if (previewAnhXeMays.value.length === 0) {
                    isErrorAnhXeMay.value = true
                    messageAnhXeMay.value = 'Vui lòng chọn ảnh!'
                    setTimeout(() => {
                        isError.value = false
                        messageError.value = ''
                    }, 3000)
                    return
                }

                const giaThueCheck = validateChuSo(String(xeMay.value.giaThue))
                if (!giaThueCheck.isValid) {
                    isErrorGiaThue.value = true
                    messageGiaThue.value = 'Giá thuê chỉ được chứa chữ số!'
                    hasError = true
                }

                if (hasError) {
                    setTimeout(() => {
                        isErrorGiaThue.value = false
                        messageGiaThue.value = ''
                    }, 3000)
                    return
                }

                isLoading.value = true
                try {
                    const formData = new FormData()
                    Object.entries(xeMay.value).forEach(([key, value]) => {
                        if (
                            key !== 'chiTietDonHangs' &&
                            key !== 'anhXeMays' &&
                            value !== undefined
                        ) {
                            formData.append(key, value || '')
                        }
                    })

                    selectedAnhXeMays.value.forEach((file) => {
                        formData.append('anhXeMayList', file)
                    })
                    deletedAnhXeMays.value.forEach((fileName) => {
                        formData.append('deletedAnhXeMays', fileName)
                    })

                    const response = await updateXeMay(String(xeMay.value.maXeMay), formData)
                    if (response.success) {
                        await router.push({ name: 'DanhSachXeMayView' })
                    }
                } catch (error) {
                    console.error('Lỗi khi cập nhật: ', error)
                } finally {
                    isLoading.value = false
                }
            }

            return {
                isError,
                messageError,
                isErrorGiaThue,
                messageGiaThue,
                danhMucXes,
                xeMay,
                handleCapNhat,
                previewAnhXeMays,
                handleAnhXeMayChange,
                removeAnhXeMay,
                isErrorAnhXeMay,
                messageAnhXeMay,
                anhXeMayInput,
                isLoading
            }
        }
    })
</script>
