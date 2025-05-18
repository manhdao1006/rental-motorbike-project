<template>
    <div class="container-fluid">
        <div class="row justify-content-evenly m-0 mt-3 mb-3">
            <div class="card-header col-xl-6">
                <h5 class="card-title mb-0">Thêm mới vai trò</h5>
            </div>
            <div class="card-header col-xl-6 text-end">
                <router-link class="text-success" :to="{ name: 'DanhSachVaiTroView' }">
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
                    <label for="tenVaiTro" class="form-label"
                        >Tên danh mục xe<span class="text-danger">*</span></label
                    >
                    <input
                        v-model="vaiTro.tenVaiTro"
                        type="text"
                        class="form-control"
                        id="tenVaiTro"
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
    <PopupLoading :isLoading="isLoading" />
</template>

<script lang="ts">
    import PopupLoading from '@/components/dungchung/PopupLoading.vue'
    import { addVaiTro } from '@/services/vaiTroService'
    import { defineComponent, Ref, ref } from 'vue'
    import { useRouter } from 'vue-router'

    export default defineComponent({
        name: 'ThemMoiVaiTro',
        components: {
            PopupLoading
        },
        setup() {
            const router = useRouter()
            const isError = ref(false)
            const messageError = ref<string>('')
            const vaiTro: Ref<Record<string, string>> = ref({})
            const isLoading = ref(false)

            const handleThemMoi = async () => {
                if (!vaiTro.value.tenVaiTro) {
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
                    Object.entries(vaiTro.value).forEach(([key, value]) => {
                        if (value !== undefined) {
                            formData.append(key, value || '')
                        }
                    })

                    const response = await addVaiTro(formData)
                    if (response.success) {
                        await router.push({ name: 'DanhSachVaiTroView' })
                    }
                } catch (error) {
                    console.error('Lỗi khi thêm mới: ', error)
                } finally {
                    isLoading.value = false
                }
            }

            return {
                isError,
                messageError,
                vaiTro,
                isLoading,
                handleThemMoi
            }
        }
    })
</script>
