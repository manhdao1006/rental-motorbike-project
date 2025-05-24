<template>
    <div class="container-fluid row mt-3 bg-white">
        <div class="col-2">
            <ThongTinKhachHang
                :source="nguoiDungKhachHang.duongDan || ''"
                :hoVaTen="nguoiDungKhachHang.hoVaTen || ''"
            />
        </div>
        <div class="col-10 row border-start pt-3 pb-3">
            <div class="col-3 me-5" style="max-height: 400px; overflow-y: auto">
                <div v-for="ht in hoiThoais" :key="String(ht.maHoiThoai)" class="conversation-item">
                    <router-link
                        :to="`/chat/${ht.maHoiThoai}/${maNguoiDungHienTai}`"
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
                <div class="mt-2 mb-2">
                    <span class="me-2"
                        ><img
                            :src="// String(ht.anhDaiDien) ||
                            'https://res.cloudinary.com/springboot-cloud/image/upload/v1739427632/user_vqmka8.png'"
                            :alt="String(doiDien?.hoVaTen)"
                            width="40px"
                            height="40px"
                    /></span>
                    <span class="fw-bold">{{ doiDien?.hoVaTen }}</span>
                </div>
                <div class="message-list" ref="messageListRef">
                    <div
                        v-for="msg in messages"
                        :key="msg.thoiGianGui"
                        :class="[
                            'message',
                            msg.maNguoiDung === maNguoiDungHienTai ? 'me' : 'other'
                        ]"
                    >
                        <div class="content">
                            <img
                                v-if="msg.maNguoiDung !== maNguoiDungHienTai"
                                :src="// doiDien?.anhDaiDien ||
                                'https://res.cloudinary.com/springboot-cloud/image/upload/v1739427632/user_vqmka8.png'"
                                alt="Avatar"
                                class="avatar"
                                width="24"
                                height="24"
                            />
                            {{ msg.noiDung }}
                        </div>
                        <div class="timestamp">{{ formatTimestamp(msg.thoiGianGui) }}</div>
                    </div>
                </div>

                <div class="input-container">
                    <input
                        ref="inputRef"
                        v-model="input"
                        @keyup.enter="send"
                        placeholder="Nhập tin nhắn..."
                    />
                    <button @click="send" class="send-btn">Gửi</button>
                </div>
            </div>
        </div>
    </div>
</template>

<script lang="ts">
    import { useDate } from '@/composables/useDate'
    import { useTime } from '@/composables/useTime'
    import { getNguoiDungByMaNguoiDung } from '@/services/authService'
    import { getHoiThoaisByKhachHang } from '@/services/chatApi'
    import { connectToChat, disconnect, fetchOldMessages, sendMessage } from '@/services/chatSocket'
    import { getMaNguoiDung } from '@/services/localStorageService'
    import { defineComponent, nextTick, onBeforeUnmount, onMounted, ref, Ref, watch } from 'vue'
    import { useRoute } from 'vue-router'
    import ThongTinKhachHang from './dungchung/ThongTinKhachHang.vue'

    export default defineComponent({
        name: 'ChatBox',
        components: {
            ThongTinKhachHang
        },
        setup() {
            const route = useRoute()
            const input = ref('')
            const messages = ref<any[]>([])
            const inputRef = ref<HTMLInputElement | null>(null)
            const messageListRef = ref<HTMLElement | null>(null)
            const nguoiDungKhachHang = ref<Record<string, null>>({})
            const khachHang = ref<Record<string, null>>({})
            const hoiThoais: Ref<Record<string, unknown>[]> = ref([])
            const maNguoiDungHienTai = getMaNguoiDung()
            const currentMaHoiThoaiDangKetNoi = ref<string | null>(null)
            const doiDien = ref<{ hoVaTen: string; anhDaiDien: string } | null>(null)
            const selectedMaHoiThoai = ref(route.params.maHoiThoai)

            watch(
                () => route.params.maHoiThoai,
                (newVal) => {
                    selectedMaHoiThoai.value = newVal
                }
            )

            const fetchKhachHang = async () => {
                const response = await getNguoiDungByMaNguoiDung(getMaNguoiDung())
                khachHang.value = response.khachHang
                nguoiDungKhachHang.value = response.nguoiDung
            }

            const fetchHoiThoais = async () => {
                const response = await getHoiThoaisByKhachHang(getMaNguoiDung())
                console.log(response)

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

            const fetchDoiDienByHoiThoai = async (hoiThoai: any) => {
                const maDoiDien =
                    getMaNguoiDung() === hoiThoai.maKhachHang
                        ? hoiThoai.maChuCuaHang
                        : hoiThoai.maKhachHang
                const { nguoiDung } = await getNguoiDungByMaNguoiDung(maDoiDien)

                doiDien.value = {
                    hoVaTen: nguoiDung.hoVaTen,
                    anhDaiDien: nguoiDung.anhDaiDien
                }
            }

            function scrollToBottom() {
                nextTick(() => {
                    if (messageListRef.value) {
                        messageListRef.value.scrollTop = messageListRef.value.scrollHeight
                    }
                })
            }

            const send = () => {
                const maHoiThoai = String(route.params.maHoiThoai)

                if (!input.value.trim()) return
                if (currentMaHoiThoaiDangKetNoi.value !== maHoiThoai) return

                const noiDungTinNhan = input.value.trim()
                const thoiGianGui = new Date().toISOString()

                sendMessage(maHoiThoai, maNguoiDungHienTai, noiDungTinNhan)
                input.value = ''
                scrollToBottom()

                const hoiThoaiCanCapNhat = hoiThoais.value.find(
                    (ht) => ht.maHoiThoai === maHoiThoai
                )
                if (hoiThoaiCanCapNhat) {
                    hoiThoaiCanCapNhat.tinNhanCuoiNoiDung = noiDungTinNhan
                    hoiThoaiCanCapNhat.tinNhanCuoiThoiGian = thoiGianGui
                }
            }

            function formatTimestamp(ts: string) {
                return useTime(ts)
            }

            function formatDate(ts: string) {
                return useDate(ts)
            }

            watch(
                () => route.params.maHoiThoai,
                async (newMaHoiThoai) => {
                    if (newMaHoiThoai) {
                        const ma = String(newMaHoiThoai)
                        messages.value = await fetchOldMessages(ma)

                        await nextTick()
                        scrollToBottom()

                        await fetchHoiThoais()

                        const hoiThoaiHienTai = hoiThoais.value.find((ht) => ht.maHoiThoai === ma)
                        if (hoiThoaiHienTai) {
                            await fetchDoiDienByHoiThoai(hoiThoaiHienTai)
                        }

                        await connectToChat(ma, (msg: unknown) => {
                            if (route.params.maHoiThoai === ma) {
                                messages.value.push(msg)
                                nextTick(() => scrollToBottom())
                            }
                        })
                        currentMaHoiThoaiDangKetNoi.value = ma
                    }
                },
                { immediate: true }
            )

            onMounted(async () => {
                await fetchKhachHang()
            })

            onBeforeUnmount(() => {
                disconnect()
            })

            return {
                input,
                nguoiDungKhachHang,
                khachHang,
                messages,
                send,
                formatTimestamp,
                formatDate,
                inputRef,
                hoiThoais,
                maNguoiDungHienTai,
                doiDien,
                messageListRef,
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
        background-color: #d1d1d1; /* màu sẫm bạn muốn */
    }

    .chat-container {
        display: flex;
        flex-direction: column;
        height: 400px;
        width: 70%;
        border: 1px solid #ccc;
        background: #fff;
        box-shadow: 0 0 8px rgba(0, 0, 0, 0.1);
    }

    .message-list {
        flex: 1 1 auto;
        overflow-y: auto;
        padding: 16px;
        background: #f0f2f5;
        display: flex;
        flex-direction: column;
        gap: 10px;
        scroll-behavior: smooth;
    }

    /* Tin nhắn người khác */
    .message.other {
        align-self: flex-start;
        background-color: #fff;
        color: #050505;
        padding: 10px 14px;
        border-radius: 18px 18px 18px 4px;
        box-shadow: 0 1px 1.5px rgb(0 0 0 / 0.13);
        max-width: 70%;
    }

    /* Tin nhắn mình */
    .message.me {
        align-self: flex-end;
        background-color: #0084ff;
        color: white;
        padding: 10px 14px;
        border-radius: 18px 18px 4px 18px;
        box-shadow: 0 1px 1.5px rgb(0 0 0 / 0.2);
        max-width: 70%;
    }

    .content {
        font-size: 15px;
        white-space: pre-wrap; /* xuống dòng tự nhiên */
    }

    .timestamp {
        font-size: 10px;
        color: #000000;
        margin-top: 4px;
        text-align: right;
    }

    .input-container {
        display: flex;
        align-items: center;
        padding: 12px 16px;
        background: #fff;
        border-top: 1px solid #ddd;
        position: relative;
    }

    input {
        flex: 1;
        border-radius: 18px;
        border: 1px solid #ddd;
        padding: 10px 14px;
        font-size: 15px;
        outline: none;
        margin-right: 10px;
        transition: border-color 0.2s;
    }

    input:focus {
        border-color: #0084ff;
    }

    button {
        background-color: transparent;
        border: none;
        cursor: pointer;
        font-size: 20px;
        color: #0084ff;
        margin-left: 6px;
        user-select: none;
    }

    button:active {
        opacity: 0.7;
    }

    .send-btn {
        font-weight: 600;
        font-size: 16px;
        color: #0084ff;
    }
</style>
