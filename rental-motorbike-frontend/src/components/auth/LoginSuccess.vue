<script lang="ts">
    import { fetchNguoiDung, getNguoiDungByMaNguoiDung } from '@/services/authService'
    import {
        getMaNguoiDung,
        setMaNguoiDung,
        setRefreshToken,
        setToken
    } from '@/services/localStorageService'
    import { defineComponent, inject, onMounted, Ref } from 'vue'
    import { useRouter } from 'vue-router'
    export default defineComponent({
        name: 'LoginSuccess',
        setup() {
            const router = useRouter()
            const isLoggedIn = inject('isLoggedIn') as Ref<boolean> | undefined

            onMounted(async () => {
                const urlParams = new URLSearchParams(window.location.search)
                const accessToken = urlParams.get('accessToken')
                const refreshToken = urlParams.get('refreshToken')

                if (accessToken && refreshToken) {
                    setToken(accessToken)
                    setRefreshToken(refreshToken)
                    const maNguoiDung = await fetchNguoiDung()
                    if (maNguoiDung) {
                        setMaNguoiDung(maNguoiDung)
                    }

                    localStorage.setItem('accessToken', accessToken)
                    localStorage.setItem('refreshToken', refreshToken)

                    window.history.replaceState({}, document.title, '/login-success')

                    const response = await getNguoiDungByMaNguoiDung(getMaNguoiDung())

                    if (isLoggedIn) isLoggedIn.value = true
                    localStorage.setItem('isLoggedIn', 'true')

                    try {
                        if (response.data?.vaiTro?.tenVaiTro?.includes('ROLE_QUANTRIVIEN')) {
                            await router.push('/quan-tri/dashboard')
                        } else {
                            await router.push('/')
                        }
                    } catch (error) {
                        console.error('Lỗi khi lấy thông tin người dùng:', error)
                    }
                } else {
                    router.push('/dang-nhap')
                }
            })
        }
    })
</script>

<template>
    <div>Đang xử lý đăng nhập...</div>
</template>
