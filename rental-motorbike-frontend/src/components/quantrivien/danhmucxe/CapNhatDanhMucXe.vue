<template>
    <div class="container-fluid">
        <div class="row justify-content-evenly m-0 mt-3 mb-3">
            <div class="card-header col-xl-6">
                <h5 class="card-title mb-0">Cập nhật danh mục xe</h5>
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
                        v-model="danhMuc.tenDanhMucXe"
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
                    title="Cập nhật"
                    @click.prevent="handleCapNhat"
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
    import { getDanhMucXeByMaDanhMucXe, updateDanhMucXe } from '@/services/danhMucXeService'
    import { defineComponent, onMounted, Ref, ref } from 'vue'
    import { useRoute, useRouter } from 'vue-router'

    export default defineComponent({
        name: 'CapNhatDanhMucXe',
        components: {
            PopupLoading
        },
        setup() {
            const route = useRoute()
            const router = useRouter()
            const isError = ref(false)
            const messageError = ref<string>('')
            const danhMuc: Ref<Record<string, string>> = ref({})
            const isLoading = ref(false)
            const isLoadingPage = ref(true)

            const fetchDanhMucXe = async () => {
                const response = await getDanhMucXeByMaDanhMucXe(String(route.params.maDanhMucXe))
                danhMuc.value = response
            }

            onMounted(async () => {
                await Promise.all([fetchDanhMucXe()])
                isLoadingPage.value = false
            })

            const handleCapNhat = async () => {
                if (!danhMuc.value.tenDanhMucXe) {
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
                    Object.entries(danhMuc.value).forEach(([key, value]) => {
                        if (key !== 'xeMays' && value !== undefined) {
                            formData.append(key, value || '')
                        }
                    })

                    const response = await updateDanhMucXe(
                        String(danhMuc.value.maDanhMucXe),
                        formData
                    )
                    if (response.success) {
                        await router.push({ name: 'DanhSachDanhMucXeView' })
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
                isError,
                messageError,
                danhMuc,
                handleCapNhat
            }
        }
    })
</script>
