<template>
    <div class="container-fluid row mt-3 bg-white pt-3">
        <div class="col-3 ms-3 me-3" style="max-height: 500px; overflow-y: auto">
            <div v-for="ht in hoiThoais" :key="String(ht.maHoiThoai)" class="conversation-item">
                <router-link
                    :to="`/tin-nhan-chu/${ht.maHoiThoai}/${maNguoiDungHienTai}`"
                    class="justify-content-between align-items-center row pt-3 pb-3 border-bottom text-black"
                    :class="{ 'bg-selected': ht.maHoiThoai === selectedMaHoiThoai }"
                >
                    <div class="col-2">
                        <img
                            :src="// String(ht.anhDaiDien) ||
                            'https://res.cloudinary.com/springboot-cloud/image/upload/v1739427632/user_vqmka8.png'"
                            :alt="String(ht.tenNguoiDung)"
                            width="20px"
                            height="20px"
                        />
                    </div>
                    <div class="col-10">
                        <div class="fw-bold">{{ ht.tenNguoiDung }}</div>
                        <div class="row text-muted mt-1" style="font-size: 12px">
                            <div
                                class="col-6 text-start text-truncate"
                                style="
                                    max-width: 100%;
                                    overflow: hidden;
                                    white-space: nowrap;
                                    text-overflow: ellipsis;
                                "
                            >
                                {{ ht.tinNhanCuoiNoiDung }}
                            </div>
                            <div class="col-4 text-end">
                                {{ formatDate(String(ht.tinNhanCuoiThoiGian)) }}
                            </div>
                        </div>
                    </div>
                </router-link>
            </div>
        </div>
        <div class="col-7 chat-container">
            <div class="text-center fst-italic mt-3">Tin nhắn trò chuyện</div>
        </div>
    </div>
</template>

<script lang="ts">
    import { useDate } from '@/composables/useDate'
    import { getNguoiDungByMaNguoiDung } from '@/services/authService'
    import { getHoiThoaisByChuCuaHang } from '@/services/chatApi'
    import { disconnect } from '@/services/chatSocket'
    import { getMaNguoiDung } from '@/services/localStorageService'
    import { defineComponent, onBeforeUnmount, onMounted, ref, Ref, watch } from 'vue'
    import { useRoute } from 'vue-router'

    export default defineComponent({
        name: 'DanhSachChatKhachHang',

        setup() {
            const route = useRoute()
            const input = ref('')
            const inputRef = ref<HTMLInputElement | null>(null)
            const hoiThoais: Ref<Record<string, unknown>[]> = ref([])
            const maNguoiDungHienTai = getMaNguoiDung()
            const selectedMaHoiThoai = ref(route.params.maHoiThoai)

            watch(
                () => route.params.maHoiThoai,
                (newVal) => {
                    selectedMaHoiThoai.value = newVal
                }
            )

            const fetchHoiThoais = async () => {
                const response = await getHoiThoaisByChuCuaHang(getMaNguoiDung())
                const danhSach = response

                const allResponses = await Promise.all(
                    danhSach.map(async (ht: any) => {
                        const maDoiDien =
                            getMaNguoiDung() === ht.maKhachHang ? ht.maChuCuaHang : ht.maKhachHang

                        const { nguoiDung } = await getNguoiDungByMaNguoiDung(maDoiDien)

                        const tinNhans = ht.tinNhans || []
                        const tinNhanCuoi =
                            tinNhans.length > 0 ? tinNhans[tinNhans.length - 1] : null

                        return {
                            ...ht,
                            tenNguoiDung: nguoiDung.hoVaTen,
                            anhDaiDien: nguoiDung.anhDaiDien,
                            tinNhanCuoiNoiDung: tinNhanCuoi?.noiDung || 'Chưa có tin nhắn',
                            tinNhanCuoiThoiGian: tinNhanCuoi?.thoiGianGui || null
                        }
                    })
                )

                hoiThoais.value = allResponses
            }

            const formatDate = (ts: string) => {
                return useDate(ts)
            }

            onMounted(async () => {
                await fetchHoiThoais()
            })

            onBeforeUnmount(() => {
                disconnect()
            })

            return {
                input,
                formatDate,
                inputRef,
                hoiThoais,
                maNguoiDungHienTai,
                selectedMaHoiThoai
            }
        }
    })
</script>

<style scoped>
    html,
    body,
    #app {
        height: 100%;
        margin: 0;
        font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
    }

    .bg-selected {
        background-color: #d1d1d1;
    }

    .chat-container {
        display: flex;
        flex-direction: column;
        height: 500px;
        width: 70%;
        border: 1px solid #ccc;
        background: #fff;
        box-shadow: 0 0 8px rgba(0, 0, 0, 0.1);
    }
</style>
