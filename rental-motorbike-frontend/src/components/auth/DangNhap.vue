<template>
    <section class="py-3 py-md-5 pt-0 bg-white">
        <div class="container">
            <div class="row">
                <div class="col-12">
                    <div class="mb-5">
                        <h2 class="display-5 fw-bold text-center text-uppercase">Đăng nhập</h2>
                        <p class="text-center m-0">
                            Bạn chưa có tài khoản? <a href="/dang-ky">Đăng ký</a>
                        </p>
                    </div>
                </div>
            </div>
            <div class="row justify-content-center">
                <div class="col-12 col-lg-10 col-xl-8">
                    <div class="row gy-5 justify-content-center">
                        <div class="col-12 col-lg-5">
                            <form @submit.prevent="handleDangNhap">
                                <div class="row gy-3 overflow-hidden">
                                    <div class="col-12">
                                        <div class="block-error">{{ error }}</div>
                                        <div class="form-floating mb-3">
                                            <input
                                                type="email"
                                                class="form-control border-0 border-bottom rounded-0"
                                                placeholder="Nhập email ..."
                                                required
                                                v-model="email"
                                            />
                                            <label for="email" class="form-label"
                                                >Email<span class="text-danger">*</span></label
                                            >
                                        </div>
                                    </div>
                                    <div class="col-12 row align-items-center">
                                        <div
                                            class="form-floating mb-3 col-xl-10 col-md-10 col-sm-10 col-10"
                                        >
                                            <input
                                                :type="showMatKhau ? 'text' : 'password'"
                                                class="form-control border-0 border-bottom rounded-0"
                                                placeholder="Nhập mật khẩu ..."
                                                required
                                                v-model="matKhau"
                                            />
                                            <label for="password" class="form-label ms-3"
                                                >Mật khẩu<span class="text-danger">*</span></label
                                            >
                                        </div>
                                        <span
                                            class="col-xl-2 col-md-2 col-sm-2 col-2 h-50 justify-content-center"
                                            @click="toggleShowPassword"
                                        >
                                            <i
                                                :class="[
                                                    'far',
                                                    showMatKhau ? 'fa-eye' : 'fa-eye-slash'
                                                ]"
                                            ></i>
                                        </span>
                                    </div>
                                    <div class="col-12">
                                        <div class="row justify-content-between">
                                            <div class="col-6">
                                                <div class="form-check">
                                                    <input
                                                        class="form-check-input"
                                                        type="checkbox"
                                                        value=""
                                                        name="remember_me"
                                                        id="remember_me"
                                                    />
                                                    <label
                                                        class="form-check-label text-secondary"
                                                        for="remember_me"
                                                    >
                                                        Nhớ mật khẩu?
                                                    </label>
                                                </div>
                                            </div>
                                            <div class="col-6">
                                                <div class="text-end">
                                                    <a
                                                        href="#!"
                                                        class="link-secondary text-decoration-none"
                                                        >Quên mật khẩu?</a
                                                    >
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="col-12">
                                        <div class="d-grid">
                                            <button
                                                class="btn btn-lg btn-dark rounded-0 fs-6"
                                                type="submit"
                                                :disabled="isLoading"
                                            >
                                                Đăng nhập
                                            </button>
                                        </div>
                                    </div>
                                    <div class="col-12">
                                        <a href="/" class="text-decoration-none fs-6">
                                            Quay lại trang chủ
                                        </a>
                                    </div>
                                </div>
                            </form>
                        </div>
                        <div
                            class="col-12 col-lg-2 d-flex align-items-center justify-content-center gap-3 flex-lg-column"
                        >
                            <div
                                class="bg-dark h-100 d-none d-lg-block"
                                style="width: 1px; --bs-bg-opacity: 0.1"
                            ></div>
                            <div
                                class="bg-dark w-100 d-lg-none"
                                style="height: 1px; --bs-bg-opacity: 0.1"
                            ></div>
                            <div class="text-uppercase">hoặc</div>
                            <div
                                class="bg-dark h-100 d-none d-lg-block"
                                style="width: 1px; --bs-bg-opacity: 0.1"
                            ></div>
                            <div
                                class="bg-dark w-100 d-lg-none"
                                style="height: 1px; --bs-bg-opacity: 0.1"
                            ></div>
                        </div>
                        <div class="col-12 col-lg-5 d-flex align-items-center">
                            <div class="d-flex gap-3 flex-column w-100">
                                <button
                                    @click="loginWithGoogle"
                                    href="#!"
                                    class="btn bsb-btn-2xl btn-outline-dark rounded-0 d-flex align-items-center"
                                >
                                    <svg
                                        xmlns="http://www.w3.org/2000/svg"
                                        width="16"
                                        height="16"
                                        fill="currentColor"
                                        class="bi bi-google text-danger"
                                        viewBox="0 0 16 16"
                                    >
                                        <path
                                            d="M15.545 6.558a9.42 9.42 0 0 1 .139 1.626c0 2.434-.87 4.492-2.384 5.885h.002C11.978 15.292 10.158 16 8 16A8 8 0 1 1 8 0a7.689 7.689 0 0 1 5.352 2.082l-2.284 2.284A4.347 4.347 0 0 0 8 3.166c-2.087 0-3.86 1.408-4.492 3.304a4.792 4.792 0 0 0 0 3.063h.003c.635 1.893 2.405 3.301 4.492 3.301 1.078 0 2.004-.276 2.722-.764h-.003a3.702 3.702 0 0 0 1.599-2.431H8v-3.08h7.545z"
                                        />
                                    </svg>
                                    <span class="ms-2 fs-6 flex-grow-1">Đăng nhập với Google</span>
                                </button>
                                <!-- <a
                                    href="#!"
                                    class="btn bsb-btn-2xl btn-outline-dark rounded-0 d-flex align-items-center"
                                >
                                    <svg
                                        xmlns="http://www.w3.org/2000/svg"
                                        width="16"
                                        height="16"
                                        fill="currentColor"
                                        class="bi bi-facebook text-primary"
                                        viewBox="0 0 16 16"
                                    >
                                        <path
                                            d="M16 8.049c0-4.446-3.582-8.05-8-8.05C3.58 0-.002 3.603-.002 8.05c0 4.017 2.926 7.347 6.75 7.951v-5.625h-2.03V8.05H6.75V6.275c0-2.017 1.195-3.131 3.022-3.131.876 0 1.791.157 1.791.157v1.98h-1.009c-.993 0-1.303.621-1.303 1.258v1.51h2.218l-.354 2.326H9.25V16c3.824-.604 6.75-3.934 6.75-7.951z"
                                        />
                                    </svg>
                                    <span class="ms-2 fs-6 flex-grow-1"
                                        >Đăng nhập với Facebook</span
                                    >
                                </a> -->
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </section>
    <PopupLoading :isLoading="isLoading" />
</template>

<script lang="ts">
    import { useTogglePassword } from '@/composables/useTogglePassword'
    import { dangNhap, getNguoiDungByMaNguoiDung } from '@/services/authService'
    import { getMaNguoiDung } from '@/services/localStorageService'
    import { defineComponent, inject, Ref, ref } from 'vue'
    import { useRouter } from 'vue-router'
    import PopupLoading from '../dungchung/PopupLoading.vue'

    export default defineComponent({
        name: 'DangNhap',
        components: {
            PopupLoading
        },
        setup() {
            const isLoggedIn = inject('isLoggedIn') as Ref<boolean> | undefined
            const email = ref('') as Ref<string>
            const matKhau = ref('') as Ref<string>
            const error = ref('') as Ref<string>
            const { showMatKhau, toggle: toggleShowPassword } = useTogglePassword()
            const router = useRouter()
            const isLoading = ref(false)

            const loginWithGoogle = () => {
                window.location.href = 'http://localhost:8080/oauth2/authorization/google'
            }

            const handleDangNhap = async () => {
                isLoading.value = true
                try {
                    const response = await dangNhap(email.value, matKhau.value)

                    if (response?.success) {
                        const result = await getNguoiDungByMaNguoiDung(getMaNguoiDung())

                        if (result.nguoiDung.trangThaiHoatDong === 'Chờ duyệt') {
                            error.value =
                                'Tài khoản của bạn đang chờ duyệt. Vui lòng liên hệ quản trị viên.'
                            return
                        }

                        if (isLoggedIn) isLoggedIn.value = true
                        localStorage.setItem('isLoggedIn', 'true')

                        if (result?.vaiTro?.tenVaiTro?.includes('ROLE_QUANTRIVIEN')) {
                            await router.push({
                                name: 'DanhSachNguoiDungView',
                                params: { maVaiTro: 'VT2' }
                            })
                        } else if (result?.vaiTro?.tenVaiTro?.includes('ROLE_CHUCUAHANG')) {
                            await router.push({ name: 'DanhSachXeMayView' })
                        } else if (result?.vaiTro?.tenVaiTro?.includes('ROLE_NHANVIEN')) {
                            await router.push({
                                name: 'DanhSachDonHangNhanVienView',
                                params: { trangThaiDonHang: 'Chờ xử lý' }
                            })
                        } else {
                            await router.push('/')
                        }
                    } else {
                        error.value = response?.message || 'Đăng nhập thất bại'
                    }
                } catch (err) {
                    console.error('Lỗi trong quá trình đăng nhập:', err)
                } finally {
                    isLoading.value = false
                }
            }

            return {
                error,
                isLoading,
                email,
                matKhau,
                showMatKhau,
                handleDangNhap,
                toggleShowPassword,
                loginWithGoogle
            }
        }
    })
</script>

<style>
    .block-error {
        color: #ed1c24;
        margin-bottom: 10px;
        font-size: 15px;
    }
</style>
