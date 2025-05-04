<template>
    <div class="col-md-3">
        <router-link :to="link">
            <div class="card shadow-sm border-0 pt-3">
                <div class="ps-4 pe-4">
                    <img
                        :src="source"
                        class="card-img-top card-img-bottom"
                        alt="..."
                        height="200px"
                    />
                </div>
                <div class="card-body">
                    <div class="card-title card-heading limited-width-slide" :title="tenXe">
                        {{ tenXe }}
                    </div>
                    <div class="card-text card-content limited-width-slide" :title="loaiXe">
                        {{ loaiXe }} - {{ tenDanhMucXe }}
                    </div>
                    <div class="card-text card-price">{{ formattedGiaThue }} VNĐ/Ngày</div>
                </div>
            </div>
        </router-link>
    </div>
</template>

<script lang="ts">
    import { defineComponent } from 'vue'

    export default defineComponent({
        name: 'SlideItem',
        props: {
            link: {
                type: [String, Object],
                required: true
            },
            source: String,
            loaiXe: String,
            tenXe: String,
            tenDanhMucXe: String,
            giaThue: String
        },
        computed: {
            formattedGiaThue(): string {
                if (!this.giaThue) return '0'
                const numberGiaThue = Number(this.giaThue)
                return numberGiaThue.toLocaleString('vi-VN')
            }
        }
    })
</script>

<style>
    .limited-width-slide {
        max-width: 230px;
        white-space: nowrap;
        overflow: hidden;
        text-overflow: ellipsis;
        vertical-align: middle;
    }
    a {
        text-decoration: none;
    }
    .card-heading {
        color: #23689b;
        font-family: 'Arial';
        font-size: 16px;
        font-weight: 700;
        line-height: 18px;
        text-transform: capitalize;
    }
    .card-content {
        color: #000000;
        font-family: 'Arial';
        font-size: 14px;
        font-weight: 500;
        line-height: 19.5px;
        text-transform: capitalize;
    }
    .card-price {
        color: #dc7916;
        font-family: 'Arial';
        font-size: 18px;
        font-weight: 600;
        line-height: 19.5px;
        margin-top: 10px;
    }
</style>
