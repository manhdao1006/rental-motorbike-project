<template>
    <nav id="sidebar" class="sidebar js-sidebar border border-end border-black border-top-0">
        <div class="sidebar-content js-simplebar">
            <a class="sidebar-brand text-center" href="/">
                <img
                    class=""
                    src="https://res.cloudinary.com/springboot-cloud/image/upload/v1740745486/DN-MOTORBIKE-REMOVE_qhw1ec.png"
                    alt="logo-BSR"
                    width="130px"
                    height="130px"
                />
            </a>

            <ul class="sidebar-nav">
                <li class="sidebar-header">Quản lý</li>

                <li class="sidebar-item" v-if="tenVaiTro === 'ROLE_QUANTRIVIEN'">
                    <router-link
                        class="sidebar-link"
                        :to="{ name: 'DanhSachNguoiDungView', params: { maVaiTro: 'VT2' } }"
                    >
                        <i class="fas fa-users"></i>
                        <span class="align-middle">Người dùng</span>
                    </router-link>
                </li>

                <li class="sidebar-item" v-if="tenVaiTro === 'ROLE_QUANTRIVIEN'">
                    <router-link class="sidebar-link" :to="{ name: 'DanhSachVaiTroView' }">
                        <i class="fa-solid fa-network-wired"></i>
                        <span class="align-middle">Vai trò</span>
                    </router-link>
                </li>
                <li class="sidebar-item" v-if="tenVaiTro === 'ROLE_QUANTRIVIEN'">
                    <router-link class="sidebar-link" :to="{ name: 'DanhSachCuaHangView' }">
                        <i class="fa-solid fa-store"></i>
                        <span class="align-middle">Cửa hàng</span>
                    </router-link>
                </li>

                <li class="sidebar-item" v-if="tenVaiTro === 'ROLE_QUANTRIVIEN'">
                    <router-link class="sidebar-link" :to="{ name: 'DanhSachDanhMucXeView' }">
                        <i class="fa-solid fa-layer-group"></i>
                        <span class="align-middle">Danh mục xe</span>
                    </router-link>
                </li>

                <li class="sidebar-item" v-if="tenVaiTro === 'ROLE_QUANTRIVIEN'">
                    <router-link class="sidebar-link" :to="{ name: 'DanhSachLoaiKhieuNaiView' }">
                        <i class="fa-solid fa-compress"></i>
                        <span class="align-middle">Loại khiếu nại</span>
                    </router-link>
                </li>

                <li class="sidebar-item" v-if="tenVaiTro === 'ROLE_CHUCUAHANG'">
                    <router-link class="sidebar-link" :to="{ name: 'DanhSachXeMayView' }">
                        <i class="fa-solid fa-motorcycle"></i>
                        <span class="align-middle">Xe máy</span>
                    </router-link>
                </li>

                <li class="sidebar-item" v-if="tenVaiTro === 'ROLE_CHUCUAHANG'">
                    <router-link class="sidebar-link" :to="{ name: 'DanhSachNhanVienCuaHangView' }">
                        <i class="fas fa-users"></i>
                        <span class="align-middle">Nhân viên</span>
                    </router-link>
                </li>

                <li class="sidebar-item" v-if="tenVaiTro === 'ROLE_CHUCUAHANG'">
                    <router-link
                        class="sidebar-link"
                        :to="{
                            name: 'DanhSachDonHangView',
                            params: { trangThaiDonHang: 'Chờ xử lý' }
                        }"
                    >
                        <i class="fa-solid fa-clipboard-list"></i>
                        <span class="align-middle">Đơn hàng</span>
                    </router-link>
                </li>

                <li class="sidebar-item" v-if="tenVaiTro === 'ROLE_CHUCUAHANG'">
                    <router-link class="sidebar-link" :to="{ name: 'DanhSachKhieuNaiView' }">
                        <i class="fa-solid fa-pen-to-square"></i>
                        <span class="align-middle">Khiếu nại</span>
                    </router-link>
                </li>

                <li class="sidebar-item" v-if="tenVaiTro === 'ROLE_CHUCUAHANG'">
                    <router-link class="sidebar-link" :to="{ name: 'DanhSachChatChuCuaHangView' }">
                        <i class="fa-solid fa-message"></i>
                        <span class="align-middle">Tin nhắn</span>
                    </router-link>
                </li>

                <li class="sidebar-item" v-if="tenVaiTro === 'ROLE_NHANVIEN'">
                    <router-link
                        class="sidebar-link"
                        :to="{
                            name: 'DanhSachDonHangNhanVienView',
                            params: { trangThaiDonHang: 'Chờ xử lý' }
                        }"
                    >
                        <i class="fa-solid fa-pen-to-square"></i>
                        <span class="align-middle">Đơn hàng</span>
                    </router-link>
                </li>

                <li class="sidebar-header">Cài đặt</li>

                <li class="sidebar-item">
                    <a class="sidebar-link" href="#!">
                        <i class="fas fa-bell"></i>
                        <span class="align-middle">Thông báo</span>
                    </a>
                </li>

                <li class="sidebar-item">
                    <a class="sidebar-link" href="#!">
                        <i class="fas fa-globe"></i>
                        <span class="align-middle">Ngôn ngữ</span>
                    </a>
                </li>
            </ul>
        </div>
    </nav>
</template>

<script lang="ts">
    import { getNguoiDungByMaNguoiDung } from '@/services/authService'
    import { getMaNguoiDung } from '@/services/localStorageService'
    import { defineComponent, onMounted, ref } from 'vue'

    export default defineComponent({
        name: 'SideBarAdmin',
        setup() {
            const tenVaiTro = ref<string | null>(null)

            onMounted(async () => {
                const maNguoiDung = getMaNguoiDung()
                if (maNguoiDung) {
                    const nguoiDung = await getNguoiDungByMaNguoiDung(maNguoiDung)
                    tenVaiTro.value = nguoiDung.vaiTro.tenVaiTro
                }
            })

            return { tenVaiTro }
        }
    })
</script>

<style>
    .sidebar,
    .sidebar-content {
        background: #ffece1;
        transition: margin-left 0.35s ease-in-out, left 0.35s ease-in-out,
            margin-right 0.35s ease-in-out, right 0.35s ease-in-out;
        overflow-y: auto;
        scrollbar-width: thin;
        scrollbar-color: #6c757d transparent;
    }
    .sidebar-content::-webkit-scrollbar {
        width: 6px;
    }

    .sidebar-content::-webkit-scrollbar-thumb {
        background-color: #6c757d;
        border-radius: 3px;
    }
    .sidebar {
        direction: ltr;
        max-width: 165px;
        min-width: 165px;
    }
    .sidebar-brand {
        color: #000000;
        display: block;
        font-size: 1.15rem;
        font-weight: 600;
        padding: 10px 8px;
    }
    .sidebar-nav {
        flex-grow: 1;
        list-style: none;
        margin-bottom: 0;
        padding-left: 0;
    }
    .sidebar-header {
        background: transparent;
        color: #000000;
        font-size: 12px;
        padding: 1.5rem 1.5rem 0.375rem;
    }
    .sidebar-link,
    a.sidebar-link {
        background: #ffece1;
        border-left: 3px solid transparent;
        color: #000000;
        cursor: pointer;
        display: block;
        font-weight: 400;
        padding: 5px 8px;
        position: relative;
        text-decoration: none;
        font-size: 13px;
    }
    .sidebar-link span {
        line-height: 30px;
        margin: 0px 40px 0px 10px;
    }
    .sidebar-link:hover,
    .sidebar-link:hover i {
        color: #ff8435;
    }
</style>
