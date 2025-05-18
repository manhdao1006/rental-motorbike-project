<template>
    <div class="container mt-5">
        <div class="heading-title-auctions">
            <span>Xe máy Quận Hải Châu</span>
            <router-link
                class="see-more"
                :to="{ name: 'DanhSachXeMayTheoQuanView', params: { maQuanHuyen: 'QH01' } }"
            >
                <span>Xem thêm</span>
                <i class="fa-solid fa-chevron-right"></i>
            </router-link>
        </div>
        <div id="carouselXeMaysQuanHaiChau" class="carousel slide" data-bs-ride="carousel">
            <div class="carousel-inner">
                <div
                    v-for="(chunk, index) in chunkedXeMaysQuanHaiChau"
                    :key="index"
                    class="carousel-item"
                    :class="{ active: index === 0 }"
                    data-bs-interval="5000"
                >
                    <div class="row">
                        <SlideItem
                            v-for="(item, i) in chunk"
                            :key="i"
                            :link="{
                                name: 'ChiTietXeMayTrangChuView',
                                params: { maXeMay: (item as any).xeMay.maXeMay }
                            }"
                            :source="(item as any).xeMay.anhXeMays[0].duongDan"
                            :loaiXe="(item as any).xeMay.loaiXe"
                            :tenDanhMucXe="((item as any).danhMucXe.tenDanhMucXe)"
                            :tenXe="(item as any).xeMay.tenXe"
                            :giaThue="(item as any).xeMay.giaThue"
                        />
                    </div>
                </div>
            </div>
            <button
                class="carousel-control-prev"
                type="button"
                data-bs-target="#carouselXeMaysQuanHaiChau"
                data-bs-slide="prev"
            >
                <span class="carousel-control-prev-icon" aria-hidden="true"></span>
                <span class="visually-hidden">Previous</span>
            </button>
            <button
                class="carousel-control-next"
                type="button"
                data-bs-target="#carouselXeMaysQuanHaiChau"
                data-bs-slide="next"
            >
                <span class="carousel-control-next-icon" aria-hidden="true"></span>
                <span class="visually-hidden">Next</span>
            </button>
        </div>
        <div class="heading-title-auctions mt-5">
            <span>Xe máy Quận Thanh Khê</span>
            <router-link
                class="see-more"
                :to="{ name: 'DanhSachXeMayTheoQuanView', params: { maQuanHuyen: 'QH02' } }"
            >
                <span>Xem thêm</span>
                <i class="fa-solid fa-chevron-right"></i>
            </router-link>
        </div>
        <div id="carouselXeMaysThanhKhe" class="carousel slide" data-bs-ride="carousel">
            <div class="carousel-inner">
                <div
                    v-for="(chunk, index) in chunkedXeMaysQuanThanhKhe"
                    :key="index"
                    class="carousel-item"
                    :class="{ active: index === 0 }"
                    data-bs-interval="4500"
                >
                    <div class="row">
                        <SlideItem
                            v-for="(item, i) in chunk"
                            :key="i"
                            :link="{
                                name: 'ChiTietXeMayTrangChuView',
                                params: { maXeMay: (item as any).xeMay.maXeMay }
                            }"
                            :source="(item as any).xeMay.anhXeMays[0].duongDan"
                            :loaiXe="(item as any).xeMay.loaiXe"
                            :tenDanhMucXe="((item as any).danhMucXe.tenDanhMucXe)"
                            :tenXe="(item as any).xeMay.tenXe"
                            :giaThue="(item as any).xeMay.giaThue"
                        />
                    </div>
                </div>
            </div>
            <button
                class="carousel-control-prev"
                type="button"
                data-bs-target="#carouselXeMaysThanhKhe"
                data-bs-slide="prev"
            >
                <span class="carousel-control-prev-icon" aria-hidden="true"></span>
                <span class="visually-hidden">Previous</span>
            </button>
            <button
                class="carousel-control-next"
                type="button"
                data-bs-target="#carouselXeMaysThanhKhe"
                data-bs-slide="next"
            >
                <span class="carousel-control-next-icon" aria-hidden="true"></span>
                <span class="visually-hidden">Next</span>
            </button>
        </div>
        <div class="heading-title-auctions mt-5">
            <span>Xe máy Quận Sơn Trà</span>
            <router-link
                class="see-more"
                :to="{ name: 'DanhSachXeMayTheoQuanView', params: { maQuanHuyen: 'QH03' } }"
            >
                <span>Xem thêm</span>
                <i class="fa-solid fa-chevron-right"></i>
            </router-link>
        </div>
        <div id="carouselXeMaysSonTra" class="carousel slide" data-bs-ride="carousel">
            <div class="carousel-inner">
                <div
                    v-for="(chunk, index) in chunkedXeMaysQuanSonTra"
                    :key="index"
                    class="carousel-item"
                    :class="{ active: index === 0 }"
                    data-bs-interval="4000"
                >
                    <div class="row">
                        <SlideItem
                            v-for="(item, i) in chunk"
                            :key="i"
                            :link="{
                                name: 'ChiTietXeMayTrangChuView',
                                params: { maXeMay: (item as any).xeMay.maXeMay }
                            }"
                            :source="(item as any).xeMay.anhXeMays[0].duongDan"
                            :loaiXe="(item as any).xeMay.loaiXe"
                            :tenDanhMucXe="((item as any).danhMucXe.tenDanhMucXe)"
                            :tenXe="(item as any).xeMay.tenXe"
                            :giaThue="(item as any).xeMay.giaThue"
                        />
                    </div>
                </div>
            </div>
            <button
                class="carousel-control-prev"
                type="button"
                data-bs-target="#carouselXeMaysSonTra"
                data-bs-slide="prev"
            >
                <span class="carousel-control-prev-icon" aria-hidden="true"></span>
                <span class="visually-hidden">Previous</span>
            </button>
            <button
                class="carousel-control-next"
                type="button"
                data-bs-target="#carouselXeMaysSonTra"
                data-bs-slide="next"
            >
                <span class="carousel-control-next-icon" aria-hidden="true"></span>
                <span class="visually-hidden">Next</span>
            </button>
        </div>
    </div>
    <PolicyLayout />
    <div class="container mt-5">
        <div class="heading-title-auctions mt-5">
            <span>Xe máy Quận Ngũ Hành Sơn</span>
            <router-link
                class="see-more"
                :to="{ name: 'DanhSachXeMayTheoQuanView', params: { maQuanHuyen: 'QH04' } }"
            >
                <span>Xem thêm</span>
                <i class="fa-solid fa-chevron-right"></i>
            </router-link>
        </div>
        <div id="carouselXeMaysQuanNguHanhSon" class="carousel slide" data-bs-ride="carousel">
            <div class="carousel-inner">
                <div
                    v-for="(chunk, index) in chunkedXeMaysQuanNguHanhSon"
                    :key="index"
                    class="carousel-item"
                    :class="{ active: index === 0 }"
                    data-bs-interval="3500"
                >
                    <div class="row">
                        <SlideItem
                            v-for="(item, i) in chunk"
                            :key="i"
                            :link="{
                                name: 'ChiTietXeMayTrangChuView',
                                params: { maXeMay: (item as any).xeMay.maXeMay }
                            }"
                            :source="(item as any).xeMay.anhXeMays[0].duongDan"
                            :loaiXe="(item as any).xeMay.loaiXe"
                            :tenDanhMucXe="((item as any).danhMucXe.tenDanhMucXe)"
                            :tenXe="(item as any).xeMay.tenXe"
                            :giaThue="(item as any).xeMay.giaThue"
                        />
                    </div>
                </div>
            </div>
            <button
                class="carousel-control-prev"
                type="button"
                data-bs-target="#carouselXeMaysQuanNguHanhSon"
                data-bs-slide="prev"
            >
                <span class="carousel-control-prev-icon" aria-hidden="true"></span>
                <span class="visually-hidden">Previous</span>
            </button>
            <button
                class="carousel-control-next"
                type="button"
                data-bs-target="#carouselXeMaysQuanNguHanhSon"
                data-bs-slide="next"
            >
                <span class="carousel-control-next-icon" aria-hidden="true"></span>
                <span class="visually-hidden">Next</span>
            </button>
        </div>
        <div class="heading-title-auctions mt-5">
            <span>Xe máy Quận Liên Chiểu</span>
            <router-link
                class="see-more"
                :to="{ name: 'DanhSachXeMayTheoQuanView', params: { maQuanHuyen: 'QH05' } }"
            >
                <span>Xem thêm</span>
                <i class="fa-solid fa-chevron-right"></i>
            </router-link>
        </div>
        <div id="carouselXeMaysQuanLienChieu" class="carousel slide" data-bs-ride="carousel">
            <div class="carousel-inner">
                <div
                    v-for="(chunk, index) in chunkedXeMaysQuanLienChieu"
                    :key="index"
                    class="carousel-item"
                    :class="{ active: index === 0 }"
                    data-bs-interval="3000"
                >
                    <div class="row">
                        <SlideItem
                            v-for="(item, i) in chunk"
                            :key="i"
                            :link="{
                                name: 'ChiTietXeMayTrangChuView',
                                params: { maXeMay: (item as any).xeMay.maXeMay }
                            }"
                            :source="(item as any).xeMay.anhXeMays[0].duongDan"
                            :loaiXe="(item as any).xeMay.loaiXe"
                            :tenDanhMucXe="((item as any).danhMucXe.tenDanhMucXe)"
                            :tenXe="(item as any).xeMay.tenXe"
                            :giaThue="(item as any).xeMay.giaThue"
                        />
                    </div>
                </div>
            </div>
            <button
                class="carousel-control-prev"
                type="button"
                data-bs-target="#carouselXeMaysQuanLienChieu"
                data-bs-slide="prev"
            >
                <span class="carousel-control-prev-icon" aria-hidden="true"></span>
                <span class="visually-hidden">Previous</span>
            </button>
            <button
                class="carousel-control-next"
                type="button"
                data-bs-target="#carouselXeMaysQuanLienChieu"
                data-bs-slide="next"
            >
                <span class="carousel-control-next-icon" aria-hidden="true"></span>
                <span class="visually-hidden">Next</span>
            </button>
        </div>
    </div>
    <PopupLoading :isLoading="isLoading" />
</template>

<script lang="ts">
    import PopupLoading from '@/components/dungchung/PopupLoading.vue'
    import { useDate } from '@/composables/useDate'
    import { useDateTime } from '@/composables/useDateTime'
    import {
        getXeMaysInQuanHaiChau,
        getXeMaysInQuanLienChieu,
        getXeMaysInQuanNguHanhSon,
        getXeMaysInQuanSonTra,
        getXeMaysInQuanThanhKhe
    } from '@/services/xeMayService'
    import { computed, defineComponent, onMounted, ref } from 'vue'
    import SlideItem from '../dungchung/SlideItem.vue'
    import PolicyLayout from './PolicyLayout.vue'

    export default defineComponent({
        name: 'ContentLayout',
        components: {
            SlideItem,
            PolicyLayout,
            PopupLoading
        },
        setup() {
            const xeMaysQuanHaiChau = ref<Record<string, undefined>[]>([])
            const xeMaysQuanThanhKhe = ref<Record<string, undefined>[]>([])
            const xeMaysQuanSonTra = ref<Record<string, undefined>[]>([])
            const xeMaysQuanNguHanhSon = ref<Record<string, undefined>[]>([])
            const xeMaysQuanLienChieu = ref<Record<string, undefined>[]>([])
            const isLoading = ref(true)

            const formatDate = (date: string) => {
                return useDate(date)
            }

            const formatDateTime = (dateTime: string) => {
                return useDateTime(dateTime)
            }

            const fetchXeMaysInQuanHaiChau = async () => {
                const response = await getXeMaysInQuanHaiChau()
                xeMaysQuanHaiChau.value = response
            }

            const chunkedXeMaysQuanHaiChau = computed(() => {
                const chunkSize = 4
                return xeMaysQuanHaiChau.value.reduce<Record<string, undefined>[][]>(
                    (result, item, index) => {
                        const chunkIndex = Math.floor(index / chunkSize)
                        if (!result[chunkIndex]) result[chunkIndex] = []
                        result[chunkIndex].push(item)
                        return result
                    },
                    []
                )
            })

            const fetchXeMaysInQuanThanhKhe = async () => {
                const response = await getXeMaysInQuanThanhKhe()
                xeMaysQuanThanhKhe.value = response
            }

            const chunkedXeMaysQuanThanhKhe = computed(() => {
                const chunkSize = 4
                return xeMaysQuanThanhKhe.value.reduce<Record<string, undefined>[][]>(
                    (result, item, index) => {
                        const chunkIndex = Math.floor(index / chunkSize)
                        if (!result[chunkIndex]) result[chunkIndex] = []
                        result[chunkIndex].push(item)
                        return result
                    },
                    []
                )
            })

            const fetchXeMaysInQuanSonTra = async () => {
                const response = await getXeMaysInQuanSonTra()
                xeMaysQuanSonTra.value = response
            }

            const chunkedXeMaysQuanSonTra = computed(() => {
                const chunkSize = 4
                return xeMaysQuanSonTra.value.reduce<Record<string, undefined>[][]>(
                    (result, item, index) => {
                        const chunkIndex = Math.floor(index / chunkSize)
                        if (!result[chunkIndex]) result[chunkIndex] = []
                        result[chunkIndex].push(item)
                        return result
                    },
                    []
                )
            })

            const fetchXeMaysInQuanNguHanhSon = async () => {
                const response = await getXeMaysInQuanNguHanhSon()
                xeMaysQuanNguHanhSon.value = response
                console.log(response)
            }

            const chunkedXeMaysQuanNguHanhSon = computed(() => {
                const chunkSize = 4
                return xeMaysQuanNguHanhSon.value.reduce<Record<string, undefined>[][]>(
                    (result, item, index) => {
                        const chunkIndex = Math.floor(index / chunkSize)
                        if (!result[chunkIndex]) result[chunkIndex] = []
                        result[chunkIndex].push(item)
                        return result
                    },
                    []
                )
            })

            const fetchXeMaysInQuanLienChieu = async () => {
                const response = await getXeMaysInQuanLienChieu()
                xeMaysQuanLienChieu.value = response
            }

            const chunkedXeMaysQuanLienChieu = computed(() => {
                const chunkSize = 4
                return xeMaysQuanLienChieu.value.reduce<Record<string, undefined>[][]>(
                    (result, item, index) => {
                        const chunkIndex = Math.floor(index / chunkSize)
                        if (!result[chunkIndex]) result[chunkIndex] = []
                        result[chunkIndex].push(item)
                        return result
                    },
                    []
                )
            })

            onMounted(async () => {
                await Promise.all([
                    fetchXeMaysInQuanHaiChau(),
                    fetchXeMaysInQuanThanhKhe(),
                    fetchXeMaysInQuanSonTra(),
                    fetchXeMaysInQuanNguHanhSon(),
                    fetchXeMaysInQuanLienChieu()
                ])
                isLoading.value = false
            })

            return {
                isLoading,
                formatDate,
                formatDateTime,
                chunkedXeMaysQuanHaiChau,
                chunkedXeMaysQuanThanhKhe,
                chunkedXeMaysQuanSonTra,
                chunkedXeMaysQuanNguHanhSon,
                chunkedXeMaysQuanLienChieu
            }
        }
    })
</script>

<style>
    .limited-width-main {
        max-width: 1000px;
        white-space: nowrap;
        overflow: hidden;
        text-overflow: ellipsis;
        display: inline-block;
        vertical-align: middle;
    }
    .heading-title-auctions {
        font-family: 'Arial';
        font-size: 20px;
        font-weight: 700;
        line-height: 20px;
        text-align: left;
        color: #23689b;
        text-transform: uppercase;
        margin: 50px 0px 30px;
    }
    .heading-title-auctions > a {
        text-transform: none;
        margin-left: 20px;
        color: #949494;
        font-size: 12px;
        font-style: italic;
    }
    .see-more {
        align-items: center;
        justify-content: center;
        padding: 8px 12px;
        border: 1px solid #000000;
        border-radius: 30px;
        gap: 5px;
        text-decoration: none;
        font-weight: 500;
        position: relative;
        transition: all 0.3s ease-in-out;
    }
    .heading-title-auctions a span {
        display: none;
        opacity: 0;
        margin-right: 6px;
    }

    .heading-title-auctions a:hover span {
        display: inline-block;
        animation: fadeIn 0.5s ease-in-out forwards;
        color: #ff8435;
    }
    .heading-title-auctions a:hover i {
        color: #ff8435;
    }
    @keyframes fadeIn {
        from {
            opacity: 0;
        }
        to {
            opacity: 1;
        }
    }

    .carousel-control-prev-icon,
    .carousel-control-next-icon {
        background-color: #efefef;
        padding: 90% 10px;
    }
    .carousel-control-prev-icon {
        margin-left: -200px;
        background-image: url("data:image/svg+xml;charset=UTF8,%3Csvg xmlns='http://www.w3.org/2000/svg' fill='%23000000' viewBox='0 0 16 16'%3E%3Cpath fill-rule='evenodd' d='M11.354 14.354a.5.5 0 0 0 0-.708L5.707 8l5.647-5.646a.5.5 0 0 0-.708-.708l-6 6a.5.5 0 0 0 0 .708l6 6a.5.5 0 0 0 .708 0z'/%3E%3C/svg%3E");
    }
    .carousel-control-next-icon {
        margin-right: -200px;
        background-image: url("data:image/svg+xml;charset=UTF8,%3Csvg xmlns='http://www.w3.org/2000/svg' fill='%23000000' viewBox='0 0 16 16'%3E%3Cpath fill-rule='evenodd' d='M4.646 14.354a.5.5 0 0 1 0-.708L10.293 8 4.646 2.354a.5.5 0 0 1 .708-.708l6 6a.5.5 0 0 1 0 .708l-6 6a.5.5 0 0 1-.708 0z'/%3E%3C/svg%3E");
    }
</style>
