<template>
    <div class="container-fluid">
        <div class="row justify-content-evenly m-0 mt-3 mb-3">
            <div class="card-header col-xl-6">
                <h5 class="card-title mb-0">Thêm mới loại khiếu nại</h5>
            </div>
            <div class="card-header col-xl-6 text-end">
                <router-link class="text-success" :to="{ name: 'DanhSachLoaiKhieuNaiView' }">
                    <i class="fas fa-chevron-circle-left"></i>
                    <span class="ps-1">Quay lại danh sách</span>
                </router-link>
            </div>
        </div>
        <div class="row m-0">
            <div v-if="isError" class="alert alert-danger">
                {{ messageError }}
            </div>
            <div class="col-xl-12">
                <div class="mb-3">
                    <label for="tenLoaiKhieuNai" class="form-label"
                        >Tên loại khiếu nại<span class="text-danger">*</span></label
                    >
                    <input
                        v-model="loaiKhieuNai.tenLoaiKhieuNai"
                        type="text"
                        class="form-control"
                        id="tenLoaiKhieuNai"
                    />
                </div>
            </div>
            <div class="text-center">
                <button
                    type="button"
                    class="btn btn-success"
                    title="Thêm mới"
                    @click.prevent="handleThemMoi"
                >
                    Thêm mới
                </button>
            </div>
        </div>
    </div>
</template>

<script lang="ts">
    import { addLoaiKhieuNai, getLoaiKhieuNais } from '@/services/nhanvien/loaiKhieuNaiService'
    import { defineComponent, onMounted, Ref, ref } from 'vue'
    import { useRouter } from 'vue-router'

    export default defineComponent({
        name: 'ThemMoiLoaiKhieuNai',
        setup() {
            const router = useRouter()
            const isError = ref(false)
            const messageError = ref<string>('')
            const loaiKhieuNai: Ref<Record<string, string>> = ref({})
            const loaiKhieuNais: Ref<Record<string, unknown>[]> = ref([])

            const fetchLoaiKhieuNais = async () => {
                const response = await getLoaiKhieuNais()
                loaiKhieuNais.value = response
            }

            onMounted(() => {
                fetchLoaiKhieuNais()
            })

            const handleThemMoi = async () => {
                if (!loaiKhieuNai.value.tenLoaiKhieuNai) {
                    isError.value = true
                    messageError.value = 'Vui lòng nhập đầy đủ các trường dữ liệu!'
                    setTimeout(() => {
                        isError.value = false
                        messageError.value = ''
                    }, 3000)
                    return
                }
                const formData = new FormData()

                Object.entries(loaiKhieuNai.value).forEach(([key, value]) => {
                    if (value !== undefined) {
                        formData.append(key, value || '')
                    }
                })

                const response = await addLoaiKhieuNai(formData)
                if (response.success) {
                    await router.push({ name: 'DanhSachLoaiKhieuNaiView' })
                }
            }

            return {
                isError,
                messageError,
                loaiKhieuNai,
                loaiKhieuNais,
                handleThemMoi
            }
        }
    })
</script>
