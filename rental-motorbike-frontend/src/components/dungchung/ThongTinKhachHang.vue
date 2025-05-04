<template>
    <ul class="nav flex-column">
        <li class="nav-item">
            <a class="nav-link custom-hover" href="#">
                <img
                    :src="
                        source ||
                        'https://res.cloudinary.com/springboot-cloud/image/upload/v1739427632/user_vqmka8.png'
                    "
                    alt=""
                    width="30px"
                    height="30px"
                />
                <span class="ms-1">{{ hoVaTen }}</span>
            </a>
        </li>
        <li class="nav-item">
            <router-link
                class="nav-link custom-hover"
                :to="{
                    name: 'CapNhatThongTinCaNhanView',
                    params: { maNguoiDung: maNguoiDung }
                }"
            >
                Thông tin cá nhân
            </router-link>
        </li>
        <li class="nav-item">
            <a class="nav-link custom-hover" href="/khach-hang/don-hang/danh-sach/Chờ xử lý"
                >Đơn hàng của tôi</a
            >
        </li>
        <li class="nav-item">
            <a class="nav-link custom-hover" href="/dang-ky-chu-cua-hang"
                >Đăng ký là chủ cửa hàng</a
            >
        </li>
        <li class="nav-item">
            <a class="nav-link custom-hover" href="" @click="handleLogout">Đăng xuất</a>
        </li>
    </ul>
</template>

<script lang="ts">
    import { dangXuat } from '@/services/authService'
    import { getMaNguoiDung } from '@/services/localStorageService'
    import { defineComponent, inject, ref } from 'vue'
    import { useRouter } from 'vue-router'

    export default defineComponent({
        name: 'ThongTinKhachHang',
        props: {
            source: String,
            hoVaTen: String
        },
        setup() {
            const isLoggedIn = inject('isLoggedIn', ref(false))
            const router = useRouter()
            const maNguoiDung = getMaNguoiDung()

            const handleLogout = () => {
                if (isLoggedIn) {
                    isLoggedIn.value = false
                }
                localStorage.removeItem('isLoggedIn')
                localStorage.removeItem('maNguoiDung')
                router.push('/trang-chu')
                dangXuat()
            }

            return { maNguoiDung, handleLogout }
        }
    })
</script>
