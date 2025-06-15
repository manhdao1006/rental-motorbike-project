<template>
    <div class="bg-custom">
        <div class="container">
            <div class="align-content-center block-navbar hide-navigation" id="blockNavbar">
                <ul class="nav justify-content-center">
                    <NavItem link="/danh-sach-xe/QH01" content="Hải Châu" />
                    <NavItem link="/danh-sach-xe/QH02" content="Thanh Khê" />
                    <NavItem link="/danh-sach-xe/QH03" content="Sơn Trà" />
                    <NavItem link="/danh-sach-xe/QH04" content="Ngũ Hành Sơn" />
                    <NavItem link="/danh-sach-xe/QH05" content="Liên Chiểu" />
                    <NavItem link="/danh-sach-xe/QH06" content="Cẩm Lệ" />
                    <NavItem link="/danh-sach-xe/QH07" content="Hòa Vang" />
                    <NavItem link="/danh-sach-xe/QH08" content="Hoàng Sa" />
                </ul>
            </div>
        </div>
    </div>
</template>

<script lang="ts">
    import { getQuanHuyens } from '@/services/authService'
    import { defineComponent, onMounted, ref } from 'vue'
    import NavItem from '../dungchung/NavItem.vue'

    export default defineComponent({
        name: 'NavigationLayout',
        components: {
            NavItem
        },
        setup() {
            const quanHuyens = ref<Record<string, undefined>[]>([])

            const fetchQuanHuyens = async () => {
                quanHuyens.value = await getQuanHuyens()
            }

            onMounted(() => {
                fetchQuanHuyens()
            })

            return { quanHuyens }
        }
    })
</script>

<style>
    .bg-custom {
        background-color: #ffece0;
        background-size: cover;
        background-position: center;
        background-repeat: no-repeat;
    }
    .block-navbar ul li a {
        position: relative;
        color: #000000;
        font-family: 'Arial';
        font-size: 16px;
        font-weight: 600;
        line-height: 20px;
        padding: 14px 10px;

        &:hover {
            color: #ff8435;
        }

        &:focus {
            color: #ff8435;
        }
    }
</style>
