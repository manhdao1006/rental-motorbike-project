<template>
    <div class="block-heading" style="font-family: Arial">
        <div class="container pt-2 pb-2">
            <div class="row justify-content-between">
                <div class="col-2">
                    <a href="/">
                        <img
                            class="resize-block-logo"
                            src="https://res.cloudinary.com/springboot-cloud/image/upload/v1740745486/DN-MOTORBIKE-REMOVE_qhw1ec.png"
                            alt="logo-BSR"
                            width="80px"
                            height="80px"
                        />
                    </a>
                </div>
                <div class="col-7 align-content-center">
                    <form class="d-flex">
                        <input
                            class="form-control me-2"
                            id="inputSearch"
                            type="search"
                            placeholder="Tìm kiếm ..."
                            aria-label="Search"
                        />
                        <button class="btn" id="buttonSearch" type="submit">
                            <i class="fas fa-search"></i>
                        </button>
                    </form>
                </div>
                <div v-if="isLoggedIn" class="col-3 row justify-content-between">
                    <div class="col-6 align-content-center block-icon-signin">
                        <div class="btn-group">
                            <button
                                type="button"
                                class="btn dropdown-toggle text-white"
                                data-bs-toggle="dropdown"
                                aria-expanded="false"
                            >
                                <img
                                    :src="
                                        nguoiDung.avatar ||
                                        'https://res.cloudinary.com/springboot-cloud/image/upload/v1739427632/user_vqmka8.png'
                                    "
                                    alt=""
                                    width="30px"
                                    height="30px"
                                />
                                <span
                                    class="text-black limited-width-header"
                                    :title="nguoiDung.email"
                                    >{{ nguoiDung.email }}</span
                                >
                            </button>
                            <ul class="dropdown-menu">
                                <li>
                                    <router-link
                                        class="dropdown-item"
                                        :to="{
                                            name: 'CapNhatThongTinCaNhanView',
                                            params: { maNguoiDung: maNguoiDung }
                                        }"
                                    >
                                        Thông tin cá nhân
                                    </router-link>
                                </li>
                                <li v-if="vaiTro.tenVaiTro === 'ROLE_QUANTRIVIEN'">
                                    <a
                                        class="dropdown-item"
                                        href="/quan-tri/nguoi-dung/danh-sach/VT2"
                                        >Trang quản lý</a
                                    >
                                </li>
                                <li v-if="vaiTro.tenVaiTro === 'ROLE_KHACHHANG'">
                                    <a class="dropdown-item" href="/dang-ky-chu-cua-hang"
                                        >Đăng ký là chủ cửa hàng</a
                                    >
                                </li>
                                <li v-if="vaiTro.tenVaiTro === 'ROLE_KHACHHANG'">
                                    <a
                                        class="dropdown-item"
                                        href="/khach-hang/don-hang/danh-sach/Chờ xử lý"
                                        >Đơn hàng của tôi</a
                                    >
                                </li>
                                <li><a class="dropdown-item" href="#">Thông báo</a></li>
                                <li><a class="dropdown-item" href="#">Cài đặt</a></li>
                                <li><hr class="dropdown-divider" /></li>
                                <li>
                                    <a class="dropdown-item" href="" @click="handleLogout"
                                        >Đăng xuất</a
                                    >
                                </li>
                            </ul>
                        </div>
                    </div>
                    <div class="col-3 align-content-center text-end">
                        <a href="/gio-hang" class="text-black">
                            <i class="fas fa-cart-plus" title="Giỏ hàng"></i>
                        </a>
                    </div>
                    <div class="col-3 align-content-center text-end">
                        <i class="fas fa-heart" title="Yêu thích"></i>
                    </div>
                </div>
                <div v-else class="col-3 row align-content-center">
                    <div class="col-6">
                        <a href="/dang-nhap" class="text-black" title="Đăng nhập">Đăng nhập</a>
                    </div>
                    <div class="col-6">
                        <a href="/dang-ky" class="text-black" title="Đăng ký">Đăng ký</a>
                    </div>
                </div>
            </div>
        </div>
    </div>
</template>

<script lang="ts">
    import { dangXuat, getNguoiDungByMaNguoiDung } from '@/services/authService'
    import { getMaNguoiDung } from '@/services/localStorageService'
    import { defineComponent, inject, onMounted, ref } from 'vue'
    import { useRouter } from 'vue-router'

    export default defineComponent({
        name: 'HeaderLayout',
        setup() {
            const isLoggedIn = inject('isLoggedIn', ref(false))
            const router = useRouter()
            const nguoiDung = ref<Record<string, string>>({})
            const vaiTro = ref<Record<string, string>>({})
            const maNguoiDung = getMaNguoiDung()

            if (isLoggedIn?.value) {
                const fetchNguoiDung = async () => {
                    const response = await getNguoiDungByMaNguoiDung(getMaNguoiDung())
                    nguoiDung.value = response.nguoiDung
                    vaiTro.value = response.vaiTro
                }

                onMounted(() => {
                    fetchNguoiDung()
                })
            }

            const handleLogout = () => {
                if (isLoggedIn) {
                    isLoggedIn.value = false
                }
                localStorage.removeItem('isLoggedIn')
                localStorage.removeItem('maNguoiDung')
                router.push('/trang-chu')
                dangXuat()
            }

            return { maNguoiDung, nguoiDung, vaiTro, isLoggedIn, handleLogout }
        }
    })
</script>

<style>
    .fas:hover {
        color: #ff8435;
        cursor: pointer;
    }
    .limited-width-header {
        max-width: 80px;
        white-space: nowrap;
        overflow: hidden;
        text-overflow: ellipsis;
        display: inline-block;
        vertical-align: middle;
    }
    .block-heading {
        background: #fff;
    }
    a {
        text-decoration: none;
    }
</style>
