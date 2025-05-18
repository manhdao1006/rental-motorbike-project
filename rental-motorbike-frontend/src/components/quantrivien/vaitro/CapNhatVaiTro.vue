<template>
    <div class="container-fluid">
        <div class="row justify-content-evenly m-0 mt-3 mb-3">
            <div class="card-header col-xl-6">
                <h5 class="card-title mb-0">Cập nhật vai trò</h5>
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
                        >Tên vai trò<span class="text-danger">*</span></label
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
    import { getVaiTroByMaVaiTro, updateVaiTro } from '@/services/vaiTroService'
    import { defineComponent, onMounted, Ref, ref } from 'vue'
    import { useRoute, useRouter } from 'vue-router'

    export default defineComponent({
        name: 'CapNhatVaiTro',
        components: {
            PopupLoading
        },
        setup() {
            const route = useRoute()
            const router = useRouter()
            const isError = ref(false)
            const messageError = ref<string>('')
            const vaiTro: Ref<Record<string, string>> = ref({})
            const isLoading = ref(false)
            const isLoadingPage = ref(true)

            const fetchVaiTro = async () => {
                const response = await getVaiTroByMaVaiTro(String(route.params.maVaiTro))
                vaiTro.value = response
            }

            onMounted(async () => {
                await Promise.all([fetchVaiTro()])
                isLoadingPage.value = false
            })

            const handleCapNhat = async () => {
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
                        if (key !== 'nguoiDungs' && value !== undefined) {
                            formData.append(key, value || '')
                        }
                    })

                    const response = await updateVaiTro(String(vaiTro.value.maVaiTro), formData)
                    if (response.success) {
                        await router.push({ name: 'DanhSachVaiTroView' })
                    }
                } catch (error) {
                    console.error('Lỗi khi cập nhật: ', error)
                } finally {
                    isLoading.value = false
                }
            }

            return {
                isLoading,
                isLoadingPage,
                isError,
                messageError,
                vaiTro,
                handleCapNhat
            }
        }
    })
</script>
