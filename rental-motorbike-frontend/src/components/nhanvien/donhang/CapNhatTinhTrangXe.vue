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
                <h5 class="card-title mb-0">Cập nhật tình trạng xe máy</h5>
            </div>
            <div class="card-header col-xl-6 text-end">
                <router-link
                    v-if="chuCuaHang.tenVaiTro === 'ROLE_NHANVIEN'"
                    class="text-success"
                    :to="{
                        name: 'ChiTietDonHangNhanVienView',
                        params: {
                            trangThaiDonHang: trangThaiDonHangParams,
                            maDonHang: donHang.maDonHang
                        }
                    }"
                >
                    <i class="fas fa-chevron-circle-left"></i>
                    <span class="ps-1">Quay lại danh sách</span>
                </router-link>
                <router-link
                    v-if="chuCuaHang.tenVaiTro === 'ROLE_CHUCUAHANG'"
                    class="text-success"
                    :to="{
                        name: 'ChiTietDonHangView',
                        params: {
                            trangThaiDonHang: trangThaiDonHangParams,
                            maDonHang: donHang.maDonHang
                        }
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
                        disabled
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
                            disabled
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
                            disabled
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
                    <input
                        v-model="xeMay.soKhung"
                        type="text"
                        class="form-control"
                        id="soKhung"
                        disabled
                    />
                </div>
                <div class="mb-3">
                    <label for="tinhTrangTruocThue" class="form-label"
                        >Tình trạng trước khi thuê<span class="text-danger">*</span></label
                    >
                    <textarea
                        :disabled="
                            trangThaiDonHangParams === 'Đã trả xe' ||
                            chuCuaHang.tenVaiTro === 'ROLE_CHUCUAHANG' ||
                            xeMay.trangThaiHoatDong === 'Có sẵn'
                        "
                        v-model="chiTietDonHang.tinhTrangTruocThue"
                        type="text"
                        class="form-control"
                        id="tinhTrangTruocThue"
                        rows="3"
                    ></textarea>
                </div>
                <div class="mb-3">
                    <label for="hinhAnhTruocThue" class="form-label"
                        >Hình ảnh trước thuê<span class="text-danger">*</span></label
                    >
                    <input
                        :disabled="
                            trangThaiDonHangParams === 'Đã trả xe' ||
                            chuCuaHang.tenVaiTro === 'ROLE_CHUCUAHANG' ||
                            xeMay.trangThaiHoatDong === 'Có sẵn'
                        "
                        type="file"
                        name="files"
                        ref="hinhAnhTruocThueInput"
                        @change="handleFileChangeTruoc"
                        accept="image/*"
                        multiple
                        class="form-control mt-2"
                    />
                    <div class="image-preview mt-3 d-flex flex-wrap">
                        <img
                            v-if="previewImageTruoc !== ''"
                            :src="previewImageTruoc || ''"
                            alt="Ảnh xem trước"
                            width="120px"
                            height="120px"
                            class="border rounded"
                        />
                    </div>
                    <p v-if="isErrorAnhXeMayTruoc" class="text-danger">
                        {{ messageAnhXeMayTruoc }}
                    </p>
                </div>
            </div>
            <div class="col-xl-6">
                <div class="mb-3">
                    <label for="tenXe" class="form-label"
                        >Tên xe máy<span class="text-danger">*</span></label
                    >
                    <input
                        v-model="xeMay.tenXe"
                        type="text"
                        class="form-control"
                        id="tenXe"
                        disabled
                    />
                </div>
                <div class="row mb-3">
                    <div class="col-xl-6">
                        <label for="mauXe" class="form-label"
                            >Màu xe<span class="text-danger">*</span></label
                        >
                        <input
                            v-model="xeMay.mauXe"
                            type="text"
                            class="form-control"
                            id="mauXe"
                            disabled
                        />
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
                            disabled
                        />
                    </div>
                </div>
                <div class="mb-3">
                    <label for="soMay" class="form-label"
                        >Số máy<span class="text-danger">*</span></label
                    >
                    <input
                        v-model="xeMay.soMay"
                        type="text"
                        class="form-control"
                        id="soMay"
                        disabled
                    />
                </div>
                <div class="mb-3">
                    <label for="tinhTrangSauThue" class="form-label"
                        >Tình trạng sau khi thuê<span class="text-danger">*</span></label
                    >
                    <textarea
                        :disabled="
                            trangThaiDonHangParams === 'Chờ xử lý' ||
                            chuCuaHang.tenVaiTro === 'ROLE_CHUCUAHANG' ||
                            xeMay.trangThaiHoatDong === 'Có sẵn'
                        "
                        v-model="chiTietDonHang.tinhTrangSauThue"
                        type="text"
                        class="form-control"
                        id="tinhTrangSauThue"
                        rows="3"
                    ></textarea>
                </div>
                <div class="mb-3">
                    <label for="hinhAnhSauThue" class="form-label"
                        >Hình ảnh sau thuê<span class="text-danger">*</span></label
                    >
                    <input
                        :disabled="
                            trangThaiDonHangParams === 'Chờ xử lý' ||
                            chuCuaHang.tenVaiTro === 'ROLE_CHUCUAHANG' ||
                            xeMay.trangThaiHoatDong === 'Có sẵn'
                        "
                        type="file"
                        name="files"
                        ref="hinhAnhSauThueInput"
                        @change="handleFileChangeSau"
                        accept="image/*"
                        multiple
                        class="form-control mt-2"
                    />
                    <div class="image-preview mt-3 d-flex flex-wrap">
                        <img
                            v-if="previewImageSau !== ''"
                            :src="previewImageSau || ''"
                            alt="Ảnh xem trước"
                            width="120px"
                            height="120px"
                            class="border rounded"
                        />
                    </div>
                    <p v-if="isErrorAnhXeMaySau" class="text-danger">{{ messageAnhXeMaySau }}</p>
                </div>
            </div>
            <div class="row"></div>
            <div
                class="text-center mt-3"
                v-if="
                    chuCuaHang.tenVaiTro !== 'ROLE_CHUCUAHANG' &&
                    xeMay.trangThaiHoatDong !== 'Có sẵn'
                "
            >
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
    import { getNguoiDungByMaNguoiDung } from '@/services/authService'
    import {
        getChiTietDonHangByMaDonHangAndMaXeMay,
        updateChiTietDonHang
    } from '@/services/chiTietDonHangService'
    import { getDanhMucXes } from '@/services/danhMucXeService'
    import { getMaNguoiDung } from '@/services/localStorageService'
    import { defineComponent, onMounted, ref } from 'vue'
    import { useRoute, useRouter } from 'vue-router'

    export default defineComponent({
        name: 'CapNhatTinhTrangXe',
        components: {
            PopupLoading
        },
        setup() {
            const route = useRoute()
            const router = useRouter()
            const isError = ref(false)
            const messageError = ref<string>('')
            const isErrorAnhXeMayTruoc = ref(false)
            const isErrorAnhXeMaySau = ref(false)
            const messageAnhXeMayTruoc = ref<string>('')
            const messageAnhXeMaySau = ref<string>('')
            const donHang = ref<Record<string, undefined>>({})
            const xeMay = ref<Record<string, undefined>>({})
            const chuCuaHang = ref<Record<string, undefined>>({})
            const chiTietDonHang = ref<Record<string, undefined>>({})
            const previewImageTruoc = ref<string | null>(null)
            const previewImageSau = ref<string | null>(null)
            const hinhAnhTruocThueInput = ref<HTMLInputElement | null>(null)
            const hinhAnhSauThueInput = ref<HTMLInputElement | null>(null)
            const danhMucXes = ref<Record<string, unknown>[]>([])
            const isLoading = ref(false)
            const isLoadingPage = ref(true)
            const trangThaiDonHangParams = String(route.params.trangThaiDonHang)

            const fetchDanhMucXes = async () => {
                const response = await getDanhMucXes()
                danhMucXes.value = response
            }

            const fetchChiTiet = async () => {
                const response = await getChiTietDonHangByMaDonHangAndMaXeMay(
                    String(route.params.maDonHang),
                    String(route.params.maXeMay)
                )
                xeMay.value = response.xeMay
                donHang.value = response.donHang
                chiTietDonHang.value = response.chiTietDonHang

                previewImageTruoc.value = chiTietDonHang.value.hinhAnhTruocThue || ''
                previewImageSau.value = chiTietDonHang.value.hinhAnhSauThue || ''
            }

            const fetchChuCuaHang = async () => {
                const response = await getNguoiDungByMaNguoiDung(getMaNguoiDung())
                chuCuaHang.value = response.vaiTro
            }

            onMounted(async () => {
                await Promise.all([fetchChiTiet(), fetchDanhMucXes(), fetchChuCuaHang()])
                isLoadingPage.value = false
            })

            const handleFileChangeTruoc = (event: Event) => {
                const target = event.target as HTMLInputElement
                const file = target.files?.[0]

                if (file) {
                    previewImageTruoc.value = URL.createObjectURL(file)
                    isError.value = false
                } else {
                    previewImageTruoc.value = null
                    isError.value = true
                }
            }

            const handleFileChangeSau = (event: Event) => {
                const target = event.target as HTMLInputElement
                const file = target.files?.[0]

                if (file) {
                    previewImageSau.value = URL.createObjectURL(file)
                    isError.value = false
                } else {
                    previewImageSau.value = null
                    isError.value = true
                }
            }

            const handleCapNhat = async () => {
                const fileHinhAnhTruocThue = hinhAnhTruocThueInput.value?.files?.[0]
                const fileHinhAnhSauThue = hinhAnhSauThueInput.value?.files?.[0]

                if (trangThaiDonHangParams === 'Chờ xử lý' && !fileHinhAnhTruocThue) {
                    isErrorAnhXeMayTruoc.value = true
                    messageAnhXeMayTruoc.value = 'Vui lòng chọn ảnh trước khi thuê xe'
                    setTimeout(() => {
                        isErrorAnhXeMayTruoc.value = false
                        messageAnhXeMayTruoc.value = ''
                    }, 3000)
                    return
                }

                if (trangThaiDonHangParams === 'Đã trả xe' && !fileHinhAnhSauThue) {
                    isErrorAnhXeMaySau.value = true
                    messageAnhXeMaySau.value = 'Vui lòng chọn ảnh sau khi thuê xe'
                    setTimeout(() => {
                        isErrorAnhXeMaySau.value = false
                        messageAnhXeMaySau.value = ''
                    }, 3000)
                    return
                }

                isLoading.value = true
                try {
                    const formData = new FormData()
                    formData.append(
                        'tinhTrangTruocThue',
                        chiTietDonHang.value.tinhTrangTruocThue || ''
                    )
                    formData.append('tinhTrangSauThue', chiTietDonHang.value.tinhTrangSauThue || '')

                    if (fileHinhAnhTruocThue) {
                        formData.append('fileHinhAnhTruocThue', fileHinhAnhTruocThue)
                    }
                    if (fileHinhAnhSauThue) {
                        formData.append('fileHinhAnhSauThue', fileHinhAnhSauThue)
                    }

                    const response = await updateChiTietDonHang(
                        String(donHang.value.maDonHang),
                        String(xeMay.value.maXeMay),
                        formData
                    )
                    if (response.success) {
                        await router.push({
                            name: 'ChiTietDonHangNhanVienView',
                            params: {
                                trangThaiDonHang: trangThaiDonHangParams,
                                maDonHang: donHang.value.maDonHang
                            }
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
                chuCuaHang,
                chiTietDonHang,
                trangThaiDonHangParams,
                isError,
                messageError,
                danhMucXes,
                xeMay,
                donHang,
                handleCapNhat,
                previewImageTruoc,
                previewImageSau,
                isErrorAnhXeMayTruoc,
                isErrorAnhXeMaySau,
                messageAnhXeMayTruoc,
                messageAnhXeMaySau,
                hinhAnhTruocThueInput,
                hinhAnhSauThueInput,
                isLoading,
                handleFileChangeTruoc,
                handleFileChangeSau
            }
        }
    })
</script>
