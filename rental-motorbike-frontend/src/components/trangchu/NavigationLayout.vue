<template>
    <div class="bg-custom">
        <div class="container">
            <div class="align-content-center block-navbar hide-navigation" id="blockNavbar">
                <ul class="nav justify-content-center">
                    <NavItem link="#" content="Hải Châu" />
                    <NavItem link="#" content="Thanh Khê" />
                    <NavItem link="#" content="Sơn Trà" />
                    <NavItem link="#" content="Ngũ Hành Sơn" />
                    <NavItem link="#" content="Liên Chiểu" />
                    <NavItem link="#" content="Cẩm Lệ" />
                    <NavItem link="#" content="Hòa Vang" />
                    <NavItem link="#" content="Hoàng Sa" />
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
