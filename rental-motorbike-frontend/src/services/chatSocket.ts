import { Client, IMessage } from '@stomp/stompjs'
import axios from 'axios'
import SockJS from 'sockjs-client'
import { getToken } from './localStorageService'

let stompClient: Client | null = null
let currentSubscription: (() => void) | null = null
let connected = false

export const connectToChat = (
    maHoiThoai: string,
    onMessage: (msg: unknown) => void
): Promise<void> => {
    return new Promise((resolve) => {
        if (stompClient && connected) {
            disconnect()
        }

        const socket = new SockJS('http://localhost:8080/ws-chat')
        stompClient = new Client({
            webSocketFactory: () => socket,
            onConnect: () => {
                connected = true

                const subscription = stompClient!.subscribe(
                    `/topic/conversation/${maHoiThoai}`,
                    (message: IMessage) => {
                        onMessage(JSON.parse(message.body))
                    }
                )

                currentSubscription = () => subscription.unsubscribe()
                resolve()
            },
            onDisconnect: () => {
                connected = false
            },
            onStompError: (frame) => {
                console.error('STOMP error:', frame)
            }
        })

        stompClient.activate()
    })
}

export const sendMessage = (maHoiThoai: string, maNguoiDung: string, noiDung: string) => {
    if (!connected || !stompClient) {
        console.error('WebSocket chưa kết nối!')
        return
    }

    stompClient.publish({
        destination: '/app/chat/send',
        body: JSON.stringify({ maHoiThoai, maNguoiDung, noiDung })
    })
}

export const fetchOldMessages = async (maHoiThoai: string): Promise<unknown[]> => {
    const res = await axios.get(`http://localhost:8080/chat/messages`, {
        params: { maHoiThoai },
        headers: {
            Authorization: `Bearer ${getToken()}`
        }
    })
    return res.data
}

export const disconnect = () => {
    if (currentSubscription) {
        currentSubscription()
        currentSubscription = null
    }

    if (stompClient) {
        stompClient.deactivate()
        stompClient = null
    }

    connected = false
}
