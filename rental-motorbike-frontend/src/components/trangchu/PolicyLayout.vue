<template>
    <div class="block-policy">
        <div class="container">
            <div class="row">
                <div class="col-xl-6 pt-5 pb-5 text-center text-black">
                    <div class="bg-white rounded-circle p-4 d-inline-block shadow">
                        <i class="fa-regular fa-thumbs-up fa-2x"></i>
                    </div>
                    <div class="fw-bolder text-uppercase mt-3 mb-1 fs-4">Không tiền đặt cọc</div>
                    <div class="">
                        Khách hàng không phải đặt cọc bất cứ khoản tiền cho việc thuê xe máy nào nếu
                        có đầy đủ các giấy tờ yêu cầu được quy định trong chính sách. Chi tiết thủ
                        tục giấy tờ vui lòng xem tại mục chính sách thuê xe của chúng tôi.
                    </div>
                </div>
                <div class="col-xl-6 pt-5 pb-5 text-center text-black">
                    <div class="bg-white rounded-circle p-4 d-inline-block shadow">
                        <i class="fa-solid fa-address-card fa-2x"></i>
                    </div>
                    <div class="fw-bolder text-uppercase mt-3 mb-1 fs-4">1 ngày thuê = 24h</div>
                    <div class="">
                        Không giống như các đơn vị cho thuê xe khác, ngày thuê xe được tính từ sáng
                        đến chiều, vậy với cùng khoảng thời gian thuê xe 24h bạn luôn phải trả nhiều
                        tiền hơn so với việc thuê xe ở chúng tôi. Cách tính 1 ngày thuê xe = 24h của
                        chúng tôi cực kỳ có lợi cho người thuê xe.
                    </div>
                </div>
            </div>
            <div class="row">
                <div class="col-xl-6 pt-5 pb-5 text-center text-black">
                    <div class="bg-white rounded-circle p-4 d-inline-block shadow">
                        <i class="fa-solid fa-motorcycle fa-2x"></i>
                    </div>
                    <div class="fw-bolder text-uppercase mt-3 mb-1 fs-4">
                        Dịch vụ giao, trả xe tận nơi
                    </div>
                    <div class="">
                        Chúng tôi cung cấp dich vụ giao xe tận nơi cho bạn với một khoản phí phát
                        sinh thấp. Chi phí giao xe sẽ được tính toán theo địa điểm giao nhận xe của
                        khách và phí mỗi lần không vượt quá 40k.
                    </div>
                </div>
                <div class="col-xl-6 pt-5 pb-5 text-center text-black">
                    <div class="bg-white rounded-circle p-4 d-inline-block shadow">
                        <i class="fa-solid fa-gears fa-2x"></i>
                    </div>
                    <div class="fw-bolder text-uppercase mt-3 mb-1 fs-4">
                        Đi bao nhiêu thanh toán bấy nhiêu
                    </div>
                    <div class="">
                        Từ ngày thuê xe thứ 2 trở đi cách tính giá thuê xe theo giờ sẽ được áp dụng,
                        với mỗi giờ phát sinh của xe số chúng tôi sẽ thu thêm 15k, xe tay ga là 20k.
                        Thuê xe quá 9 tiếng sẽ được tính tròn 1 ngày thuê xe.
                    </div>
                </div>
            </div>
        </div>
    </div>
</template>

<script lang="ts">
    import { defineComponent } from 'vue'

    export default defineComponent({
        name: 'PolicyLayout'
    })
</script>

<style>
    .block-policy {
        background-color: #ebeced;
    }
</style>
