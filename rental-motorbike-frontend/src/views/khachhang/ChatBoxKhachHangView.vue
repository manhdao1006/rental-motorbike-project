<template>
    <HeaderLayout />
    <NavigationLayout />
    <ChatBoxKhachHang />
    <FooterLayout />

    <button id="backToTop" class="btn btn-primary rounded-circle shadow" @click="scrollToTop">
        <i class="fa-solid fa-angle-up"></i>
    </button>
</template>

<script lang="ts">
    import ChatBoxKhachHang from '@/components/khachhang/ChatBoxKhachHang.vue'
    import FooterLayout from '@/components/trangchu/FooterLayout.vue'
    import HeaderLayout from '@/components/trangchu/HeaderLayout.vue'
    import NavigationLayout from '@/components/trangchu/NavigationLayout.vue'
    import { defineComponent, onMounted, onUnmounted } from 'vue'

    export default defineComponent({
        name: 'ChatBoxKhachHangView',
        components: {
            ChatBoxKhachHang,
            HeaderLayout,
            NavigationLayout,
            FooterLayout
        },
        setup() {
            const handleScroll = () => {
                const button = document.getElementById('backToTop')
                if (button) {
                    button.style.display = window.scrollY > 300 ? 'block' : 'none'
                }
            }

            const scrollToTop = () => {
                window.scrollTo({ top: 0, behavior: 'smooth' })
            }

            onMounted(() => {
                window.addEventListener('scroll', handleScroll)
            })

            onUnmounted(() => {
                window.removeEventListener('scroll', handleScroll)
            })

            return { scrollToTop }
        }
    })
</script>
