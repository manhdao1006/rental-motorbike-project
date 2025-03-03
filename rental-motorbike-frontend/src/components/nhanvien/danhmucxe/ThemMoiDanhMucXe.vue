<template>
    <div class="container-fluid">
        <div class="row justify-content-evenly m-0 mt-3 mb-3">
            <div class="card-header col-xl-6">
                <h5 class="card-title mb-0">Thêm mới danh mục xe</h5>
            </div>
            <div class="card-header col-xl-6 text-end">
                <router-link class="text-success" :to="{ name: 'DanhSachDanhMucXeView' }">
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
                    <label for="tenDanhMucXe" class="form-label"
                        >Tên danh mục xe<span class="text-danger">*</span></label
                    >
                    <input
                        v-model="danhMucXe.tenDanhMucXe"
                        type="text"
                        class="form-control"
                        id="tenDanhMucXe"
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
    import { addDanhMucXe, getDanhMucXes } from '@/services/nhanvien/danhMucXeService'
    import { defineComponent, onMounted, Ref, ref } from 'vue'
    import { useRouter } from 'vue-router'

    export default defineComponent({
        name: 'ThemMoiDanhMucXe',
        setup() {
            const router = useRouter()
            const isError = ref(false)
            const messageError = ref<string>('')
            const danhMucXe: Ref<Record<string, string>> = ref({})
            const danhMucXes: Ref<Record<string, unknown>[]> = ref([])

            const fetchDanhMucXes = async () => {
                const response = await getDanhMucXes()
                danhMucXes.value = response
            }

            onMounted(() => {
                fetchDanhMucXes()
            })

            const handleThemMoi = async () => {
                if (!danhMucXe.value.tenDanhMucXe) {
                    isError.value = true
                    messageError.value = 'Vui lòng nhập đầy đủ các trường dữ liệu!'
                    setTimeout(() => {
                        isError.value = false
                        messageError.value = ''
                    }, 3000)
                    return
                }
                const formData = new FormData()

                Object.entries(danhMucXe.value).forEach(([key, value]) => {
                    if (value !== undefined) {
                        formData.append(key, value || '')
                    }
                })

                const response = await addDanhMucXe(formData)
                if (response.success) {
                    await router.push({ name: 'DanhSachDanhMucXeView' })
                }
            }

            return {
                isError,
                messageError,
                danhMucXe,
                danhMucXes,
                handleThemMoi
            }
        }
    })
</script>
