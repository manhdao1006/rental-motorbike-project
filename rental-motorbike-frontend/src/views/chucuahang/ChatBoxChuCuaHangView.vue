<template>
    <div class="wrapper-admin">
        <SideBarAdmin />
        <div class="main">
            <HeaderAdmin />
            <ChatBoxChuCuaHang />
        </div>
    </div>
</template>

<script lang="ts">
    import ChatBoxChuCuaHang from '@/components/chucuahang/ChatBoxChuCuaHang.vue'
    import HeaderAdmin from '@/components/dungchung/HeaderAdmin.vue'
    import SideBarAdmin from '@/components/dungchung/SideBarAdmin.vue'
    import { defineComponent } from 'vue'

    export default defineComponent({
        name: 'ChatBoxChuCuaHangView',
        components: {
            ChatBoxChuCuaHang,
            SideBarAdmin,
            HeaderAdmin
        }
    })
</script>

<style>
    .wrapper-admin {
        align-items: stretch;
        background: #222e3c;
        display: flex;
        width: 100%;
    }
    .main {
        background: #f5f7fb;
        border-bottom-left-radius: 0;
        border-top-left-radius: 0;
        display: flex;
        flex-direction: column;
        min-height: 100vh;
        min-width: 0;
        overflow: hidden;
        transition: margin-left 0.35s ease-in-out, left 0.35s ease-in-out,
            margin-right 0.35s ease-in-out, right 0.35s ease-in-out;
        width: 100%;
    }
    .fs-header-admin {
        font-size: 14px;
    }
</style>
