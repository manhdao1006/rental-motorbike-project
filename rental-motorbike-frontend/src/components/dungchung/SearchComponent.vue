<template>
    <nav class="navbar navbar-light bg-light">
        <div class="container-fluid">
            <form class="d-flex w-100" @submit.prevent="handleSearch">
                <input
                    v-model="keyword"
                    class="form-control me-2"
                    type="search"
                    placeholder="Nhập từ khóa ..."
                    aria-label="Search"
                />
            </form>
        </div>
    </nav>
</template>

<script lang="ts">
    import { defineComponent, ref, watch } from 'vue'

    export default defineComponent({
        name: 'SearchComponent',
        props: {
            modelValue: String
        },
        emits: ['update:modelValue'],
        setup(props, { emit }) {
            const keyword = ref(props.modelValue || '')

            const handleSearch = () => {
                emit('update:modelValue', keyword.value)
            }

            watch(keyword, (newKeyword) => {
                emit('update:modelValue', newKeyword)
            })

            return { keyword, handleSearch }
        }
    })
</script>

<style>
    .btn-outline-success {
        border: solid 1px #ff8435;
        color: #000000;
    }
    .btn-outline-success:hover {
        background-color: #ff8435;
        color: #ffffff;
        border: solid 1px #ff8435;
    }
</style>
