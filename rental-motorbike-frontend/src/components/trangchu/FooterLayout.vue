<template>
    <div class="block-about">
        <div class="container">
            <div class="row">
                <div class="col-4 pt-5 pb-5">
                    <div class="fw-bolder">Liên hệ</div>
                    <div class="text-item">
                        <i class="fa fa-phone me-1"></i>
                        <span>Hotline: 02438438220</span>
                    </div>
                    <div class="text-item">
                        <i class="fa-solid fa-envelope me-1"></i>
                        <span>contact@motorbike.vn</span>
                    </div>
                </div>
                <div class="col-4 pt-5 pb-5">
                    <div class="fw-bolder">Về chúng tôi</div>
                    <div class="text-item">
                        <i class="fa-solid fa-chevron-down me-1"></i>
                        <span>Giới thiệu, đội ngũ</span>
                    </div>
                    <div class="text-item">
                        <i class="fa-solid fa-chevron-down me-1"></i>
                        <span>Chính sách & Thủ tục thuê xe</span>
                    </div>
                    <div class="text-item">
                        <i class="fa-solid fa-chevron-down me-1"></i>
                        <span>Chính sách bảo mật thông tin cá nhân</span>
                    </div>
                    <div class="text-item">
                        <i class="fa-solid fa-chevron-down me-1"></i>
                        <span>Hệ thống cửa hàng</span>
                    </div>
                    <div class="text-item">
                        <i class="fa-solid fa-chevron-down me-1"></i>
                        <span>FAQs</span>
                    </div>
                    <div class="text-item">
                        <i class="fa-solid fa-chevron-down me-1"></i>
                        <span>Liên hệ, hợp tác</span>
                    </div>
                </div>
                <div class="col-4 pt-5 pb-5 text-end">
                    <a href="/">
                        <img
                            class="resize-block-logo"
                            src="https://res.cloudinary.com/springboot-cloud/image/upload/v1740745486/DN-MOTORBIKE-REMOVE_qhw1ec.png"
                            alt="logo-BSR"
                            width="60%"
                        />
                    </a>
                </div>
            </div>
            <div class="text-center">
                <i class="p-4 fs-4 fa-brands fa-x-twitter"></i>
                <i class="p-4 fs-4 fa-brands fa-facebook-f"></i>
                <i class="p-4 fs-4 fa-brands fa-pinterest-p"></i>
                <i class="p-4 fs-4 fa-brands fa-linkedin-in"></i>
                <i class="p-4 fs-4 fa-brands fa-youtube"></i>
                <i class="p-4 fs-4 fa-brands fa-instagram"></i>
            </div>
        </div>
    </div>
    <div class="block-footer">
        <div class="container">
            <div class="row">
                <div class="content-footer">
                    <div>Copyright © 2025 DN - Motorbike</div>
                </div>
            </div>
        </div>
    </div>
</template>

<script lang="ts">
    import { defineComponent } from 'vue'

    export default defineComponent({
        name: 'FooterLayout'
    })
</script>

<style>
    /* css introduction */
    .block-about {
        font-family: 'Arial';
        background-color: #ffece0;
        font-size: 20px;
    }
    .text-item {
        font-size: 16px;
        color: gray;
        margin-top: 10px;
        cursor: pointer;

        &:hover {
            color: #ff8435;
        }
    }

    /* css footer */
    .block-footer {
        background: #04002e;
    }
    .content-footer {
        font-family: 'Arial';
        font-size: 15.38px;
        font-weight: 400;
        line-height: 25px;
        color: #ffffff;
        margin: 20px 0px 45px;
        text-align: center;
    }
</style>
