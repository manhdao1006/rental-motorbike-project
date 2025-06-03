<template>
    <div class="container-fluid row mt-3 bg-white">
        <div class="col-2">
            <ThongTinKhachHang
                :source="nguoiDungKhachHang.duongDan || ''"
                :hoVaTen="nguoiDungKhachHang.hoVaTen || ''"
            />
        </div>
        <div class="col-10 border-start border-dark-subtle">
            <div class="row justify-content-center">
                <div class="btn-group">
                    <router-link
                        :to="{
                            name: 'DanhSachDonHangKhachHangView',
                            params: { trangThaiDonHang: 'Chờ xử lý' }
                        }"
                        class="btn btn-primary text-uppercase border-start border-end"
                        :class="{ active: trangThaiDonHangParams === 'Chờ xử lý' }"
                        >Chờ xử lý</router-link
                    >
                    <router-link
                        :to="{
                            name: 'DanhSachDonHangKhachHangView',
                            params: { trangThaiDonHang: 'Đang giao xe' }
                        }"
                        class="btn btn-primary text-uppercase border-start"
                        :class="{ active: trangThaiDonHangParams === 'Đang giao xe' }"
                        >Đang giao xe</router-link
                    >
                    <router-link
                        :to="{
                            name: 'DanhSachDonHangKhachHangView',
                            params: { trangThaiDonHang: 'Đã giao xe' }
                        }"
                        class="btn btn-primary text-uppercase border-start"
                        :class="{ active: trangThaiDonHangParams === 'Đã giao xe' }"
                        >Đã giao xe</router-link
                    >
                    <router-link
                        :to="{
                            name: 'DanhSachDonHangKhachHangView',
                            params: { trangThaiDonHang: 'Đã trả xe' }
                        }"
                        class="btn btn-primary text-uppercase border-start"
                        :class="{ active: trangThaiDonHangParams === 'Đã trả xe' }"
                        >Đã trả xe</router-link
                    >
                    <router-link
                        :to="{
                            name: 'DanhSachDonHangKhachHangView',
                            params: { trangThaiDonHang: 'Đã hủy' }
                        }"
                        class="btn btn-primary text-uppercase border-start"
                        :class="{ active: trangThaiDonHangParams === 'Đã hủy' }"
                        >Đã hủy</router-link
                    >
                </div>
            </div>
            <div class="row justify-content-evenly m-0 mt-3 mb-3">
                <div class="card-header col-xl-6">
                    <h5 class="card-title mb-0">
                        Đơn hàng {{ donHang.maDonHang }} >>
                        <a :href="donHang?.tepDinhKem ?? '#'">Tải hợp đồng</a>
                    </h5>
                </div>
                <div class="card-header col-xl-6 text-end">
                    <router-link
                        class="text-success"
                        :to="{
                            name: 'DanhSachDonHangKhachHangView',
                            params: { trangThaiDonHang: trangThaiDonHangParams }
                        }"
                    >
                        <i class="fas fa-chevron-circle-left"></i>
                        <span class="ps-1">Quay lại danh sách</span>
                    </router-link>
                </div>
            </div>
            <div class="row" v-if="trangThaiDonHangParams === 'Đang giao xe'">
                <div ref="mapRef" style="height: 300px; width: 100%"></div>
                <p v-if="loading">Đang xử lý...</p>
                <p v-else-if="error">Lỗi: {{ error }}</p>
            </div>
            <div class="row m-0">
                <div v-if="isError" class="alert alert-danger">
                    {{ messageError }}
                </div>
                <div
                    class="row"
                    v-if="
                        trangThaiDonHangParams !== 'Đang giao xe' &&
                        trangThaiDonHangParams !== 'Đã giao xe' &&
                        trangThaiDonHangParams !== 'Đã trả xe'
                    "
                >
                    <div class="col-6">
                        <div class="mb-3">
                            <label for="trangThaiDonHang" class="form-label"
                                >Trạng thái đơn hàng<span class="text-danger">*</span></label
                            >
                            <select
                                :disabled="trangThaiDonHangParams === 'Đã hủy'"
                                class="form-select"
                                aria-label="Default select example"
                                v-model="donHang.trangThaiDonHang"
                            >
                                <option selected disabled>Chọn trạng thái</option>
                                <option value="Chờ xử lý">Chờ xử lý</option>
                                <option value="Đã hủy">Hủy đơn</option>
                            </select>
                        </div>
                    </div>
                    <div class="col-6">
                        <div class="mb-3" v-if="donHang.trangThaiDonHang === 'Đã hủy'">
                            <label for="lyDoHuy" class="form-label"
                                >Lý do hủy<span class="text-danger">*</span></label
                            >
                            <input
                                :disabled="trangThaiDonHangParams === 'Đã hủy'"
                                v-model="donHang.lyDoHuy"
                                type="text"
                                class="form-control"
                                id="lyDoHuy"
                            />
                        </div>
                        <div
                            class="mb-3"
                            v-if="
                                donHang.trangThaiDonHang === 'Chờ xử lý' &&
                                tenNhanViens[String(donHang.maNhanVien)]
                            "
                        >
                            <label for="maNhanVien" class="form-label"
                                >Nhân viên giao xe<span class="text-danger">*</span></label
                            >
                            <input
                                v-model="tenNhanViens[String(donHang.maNhanVien)]"
                                type="text"
                                class="form-control"
                                id="lyDoHuy"
                                disabled
                            />
                        </div>
                    </div>
                </div>
                <div class="row">
                    <div class="col-8">
                        <div class="text-black fw-bolder">Thông tin đơn hàng</div>
                        <table class="table table-bordered my-0">
                            <thead>
                                <tr>
                                    <th>STT</th>
                                    <th>Tên xe</th>
                                    <th>Biển số xe</th>
                                    <th>Giá thuê</th>
                                    <th>Từ ngày</th>
                                    <th>Đến ngày</th>
                                </tr>
                            </thead>
                            <tbody>
                                <tr
                                    v-for="(item, index) in xeMays"
                                    :key="(item as any).xeMay.maXeMay"
                                >
                                    <td>
                                        {{ index + 1 }}
                                    </td>
                                    <td>
                                        {{ (item as any).xeMay.tenXe }}
                                        <br /><img
                                            :src="(item as any).anhXeMays[0].duongDan"
                                            :alt="(item as any).xeMay.tenXe"
                                            width="180px"
                                            height="120px"
                                        />
                                    </td>
                                    <td>
                                        {{ (item as any).xeMay.bienSoXe }}
                                    </td>
                                    <td>{{ formattedGiaThue((item as any).xeMay.giaThue) }} VNĐ</td>
                                    <td>{{ formatDate(String((item as any).tuNgay)) }}</td>
                                    <td>{{ formatDate(String((item as any).denNgay)) }}</td>
                                </tr>
                            </tbody>
                        </table>
                        <div class="card-price">
                            Tổng tiền: {{ formattedTongTien(tongTien) }} VNĐ
                        </div>
                    </div>
                    <div class="col-4 bg-white p-4">
                        <div>
                            <div class="text-black fw-bolder text-center">Thông tin cửa hàng</div>
                            <div class="row">
                                <div class="col-4 title-user">Quản lý:</div>
                                <div class="col-8 content-user">
                                    {{ nguoiDungChuCuaHang.hoVaTen }}
                                </div>
                            </div>
                            <div class="row">
                                <div class="col-4 title-user">Cửa hàng:</div>
                                <div class="col-8 content-user">{{ chuCuaHang.tenCuaHang }}</div>
                            </div>
                            <div class="row">
                                <div class="col-4 title-user">Địa chỉ:</div>
                                <div class="col-8 content-user">{{ chuCuaHang.diaChiCuaHang }}</div>
                            </div>
                            <div class="row">
                                <div class="col-4 title-user">SĐT:</div>
                                <div class="col-8 content-user">
                                    {{ chuCuaHang.soDienThoaiCuaHang }}
                                </div>
                            </div>
                        </div>
                        <div v-if="nguoiDungNhanVien && nguoiDungNhanVien.hoVaTen">
                            <div class="text-black fw-bolder mt-3 text-center border-top">
                                Thông tin nhân viên giao xe
                            </div>
                            <div class="row">
                                <div class="col-4 title-user">Họ và tên:</div>
                                <div class="col-8 content-user">
                                    {{ nguoiDungNhanVien.hoVaTen }}
                                </div>
                            </div>
                            <div class="row">
                                <div class="col-4 title-user">Email:</div>
                                <div class="col-8 content-user">{{ nguoiDungNhanVien.email }}</div>
                            </div>
                            <div class="row">
                                <div class="col-4 title-user">Giới tính:</div>
                                <div class="col-8 content-user">
                                    {{ nguoiDungNhanVien.gioiTinh }}
                                </div>
                            </div>
                            <div class="row">
                                <div class="col-4 title-user">Ngày sinh:</div>
                                <div class="col-8 content-user">
                                    {{
                                        nguoiDungNhanVien.ngaySinh
                                            ? formatDate(String(nguoiDungNhanVien.ngaySinh))
                                            : 'NaN'
                                    }}
                                </div>
                            </div>
                            <div class="row">
                                <div class="col-4 title-user">SĐT:</div>
                                <div class="col-8 content-user">
                                    {{ nguoiDungNhanVien.soDienThoai }}
                                </div>
                            </div>
                            <div class="row">
                                <div class="col-4 title-user">Số CCCD:</div>
                                <div class="col-8 content-user">
                                    {{ nguoiDungNhanVien.soCCCD }}
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="row bg-white mt-2 ms-0 pt-3">
                    <div class="col-6">
                        <div class="row">
                            <div class="col-6">
                                <div class="mb-3">
                                    <label for="ngayGiaoXe" class="form-label"
                                        >Ngày giao xe<span class="text-danger">*</span></label
                                    >
                                    <input
                                        v-model="donHang.ngayGiaoXe"
                                        type="datetime-local"
                                        class="form-control"
                                        id="ngayGiaoXe"
                                        :min="minGiaoXeDate"
                                        :max="maxGiaoXeDate"
                                        disabled
                                    />
                                </div>
                            </div>
                            <div class="col-6">
                                <div class="mb-3">
                                    <label for="ngayTraXe" class="form-label"
                                        >Ngày trả xe<span class="text-danger">*</span></label
                                    >
                                    <input
                                        v-model="donHang.ngayTraXe"
                                        type="datetime-local"
                                        class="form-control"
                                        id="ngayTraXe"
                                        :min="minTraXeDate"
                                        :max="maxTraXeDate"
                                        disabled
                                    />
                                </div>
                            </div>
                        </div>
                        <div class="mb-3">
                            <label for="phuongThucNhanXe" class="form-label"
                                >Phương thức nhận xe<span class="text-danger">*</span></label
                            >
                            <select
                                v-model="donHang.phuongThucNhanXe"
                                class="form-select"
                                aria-label="Default select example"
                                disabled
                            >
                                <option selected disabled>Chọn phương thức nhận xe</option>
                                <option value="Nhận tại cửa hàng">Nhận tại cửa hàng</option>
                                <option value="Giao xe tận nơi">Giao xe tận nơi</option>
                            </select>
                        </div>
                    </div>
                    <div class="col-6">
                        <div class="mb-3">
                            <label for="phuongThucThanhToan" class="form-label"
                                >Phương thức thanh toán<span class="text-danger">*</span></label
                            >
                            <select
                                v-model="donHang.phuongThucThanhToan"
                                class="form-select"
                                aria-label="Default select example"
                                disabled
                            >
                                <option selected disabled>Chọn phương thức thanh toán</option>
                                <option value="Thanh toán bằng tiền mặt">
                                    Thanh toán bằng tiền mặt
                                </option>
                                <option value="Thanh toán qua ngân hàng">
                                    Thanh toán qua ngân hàng
                                </option>
                            </select>
                        </div>
                        <div class="mb-3">
                            <label for="lyDoThueXe" class="form-label"
                                >Lý do thuê xe<span class="text-danger">*</span></label
                            >
                            <textarea
                                v-model="donHang.lyDoThueXe"
                                type="text"
                                class="form-control"
                                id="lyDoThueXe"
                                disabled
                            ></textarea>
                        </div>
                    </div>
                    <div class="mb-3">
                        <label for="diaChiGiaoXe" class="form-label"
                            >Địa chỉ giao xe<span class="text-danger">*</span></label
                        >
                        <textarea
                            :value="donHang.diaChiGiaoXe || nguoiDungKhachHang.diaChi"
                            type="text"
                            class="form-control"
                            id="diaChiGiaoXe"
                            rows="1"
                            disabled
                        ></textarea>
                    </div>
                    <div
                        class="text-danger"
                        v-if="
                            donHang.trangThaiThanhToan === 'Chưa thanh toán' &&
                            !daTienHanhThanhToan &&
                            donHang.trangThaiDonHang === 'Chờ xử lý' &&
                            donHang.phuongThucThanhToan === 'Thanh toán qua ngân hàng'
                        "
                    >
                        Lưu ý: Quý khách có 24 giờ kể từ thời điểm đặt xe để hoàn tất việc đặt cọc
                        hoặc thanh toán. Quá thời hạn, đơn hàng có thể bị hủy.
                    </div>
                </div>
                <div
                    class="row"
                    v-if="
                        (donHang.trangThaiThanhToan === 'Chưa thanh toán' ||
                            donHang.trangThaiThanhToan === 'Đặt cọc') &&
                        daTienHanhThanhToan &&
                        (donHang.trangThaiDonHang === 'Chờ xử lý' ||
                            donHang.trangThaiDonHang === 'Đã giao xe') &&
                        donHang.phuongThucThanhToan === 'Thanh toán qua ngân hàng'
                    "
                >
                    <label for="bankCode">Chọn ngân hàng:</label><br />
                    <div v-for="bank in banks" :key="bank.code" class="">
                        <input
                            type="radio"
                            :id="bank.code"
                            :name="bank.name"
                            :value="bank.code"
                            v-model="selectedBankCode"
                            :checked="selectedBankCode === bank.code"
                        />
                        <label :for="bank.code" class="bank-button">
                            <img
                                class=""
                                width="100px"
                                height="40px"
                                :src="bank.logo"
                                :alt="bank.name"
                            />
                            <span class="ms-5">{{ bank.name }}</span>
                        </label>
                    </div>
                </div>
                <div class="mt-3 mb-3 row">
                    <div
                        class="text-center col-6"
                        v-if="
                            donHang.trangThaiThanhToan === 'Chưa thanh toán' &&
                            daTienHanhThanhToan &&
                            donHang.trangThaiDonHang === 'Chờ xử lý' &&
                            donHang.phuongThucThanhToan === 'Thanh toán qua ngân hàng'
                        "
                    >
                        <button
                            type="button"
                            class="btn btn-primary"
                            title="Đặt cọc 20%"
                            @click.prevent="handleDatCoc"
                            :disabled="isLoading"
                        >
                            Đặt cọc 20%
                        </button>
                    </div>
                    <div
                        class="text-center col-6"
                        v-if="
                            donHang.trangThaiThanhToan === 'Chưa thanh toán' &&
                            daTienHanhThanhToan &&
                            donHang.trangThaiDonHang === 'Chờ xử lý' &&
                            donHang.phuongThucThanhToan === 'Thanh toán qua ngân hàng'
                        "
                    >
                        <button
                            type="button"
                            class="btn btn-primary"
                            title="Thanh toán toàn bộ"
                            @click.prevent="handleThanhToan"
                            :disabled="isLoading"
                        >
                            Thanh toán toàn bộ
                        </button>
                    </div>
                    <div
                        class="text-center"
                        v-if="
                            donHang.trangThaiThanhToan === 'Đặt cọc' &&
                            daTienHanhThanhToan &&
                            donHang.trangThaiDonHang === 'Đã giao xe' &&
                            donHang.phuongThucThanhToan === 'Thanh toán qua ngân hàng'
                        "
                    >
                        <button
                            type="button"
                            class="btn btn-primary"
                            title="Thanh toán"
                            @click.prevent="handleThanhToanConLai"
                            :disabled="isLoading"
                        >
                            Thanh toán
                        </button>
                    </div>
                    <div
                        class="text-center"
                        v-if="
                            (donHang.trangThaiThanhToan === 'Chưa thanh toán' ||
                                donHang.trangThaiThanhToan === 'Đặt cọc') &&
                            !daTienHanhThanhToan &&
                            (donHang.trangThaiDonHang === 'Chờ xử lý' ||
                                donHang.trangThaiDonHang === 'Đã giao xe') &&
                            donHang.phuongThucThanhToan === 'Thanh toán qua ngân hàng'
                        "
                    >
                        <button
                            type="button"
                            class="btn btn-primary"
                            title="Tiến hành thanh toán"
                            @click.prevent="handleTienHanhThanhToan"
                            :disabled="isLoading"
                        >
                            Tiến hành thanh toán
                        </button>
                    </div>
                    <div
                        class="text-center"
                        v-if="
                            donHang.trangThaiDonHang === 'Đã hủy' &&
                            trangThaiDonHangParams !== 'Đã hủy'
                        "
                    >
                        <button
                            type="button"
                            class="btn btn-success"
                            title="Xác nhận"
                            @click.prevent="handleXacNhan"
                            :disabled="isLoading"
                        >
                            Xác nhận
                        </button>
                    </div>
                    <div class="text-center" v-if="trangThaiDonHangParams === 'Đã trả xe'">
                        <router-link
                            :to="{
                                name: 'GuiKhieuNaiView',
                                params: { maDonHang: donHang.maDonHang }
                            }"
                            class="btn btn-success"
                            title="Khiếu nại"
                        >
                            Khiếu nại
                        </router-link>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <PopupLoading :isLoading="isLoading" />
    <PopupLoading :isLoading="isLoadingPage" />
</template>
<script lang="ts">
    import PopupLoading from '@/components/dungchung/PopupLoading.vue'
    import { useDate } from '@/composables/useDate'
    import { getChuCuaHangByMaXeMay } from '@/services/chuCuaHangService'
    import { getDonHangByMaDonHang, updateDonHang } from '@/services/donHangService'
    import { getKhachHangByMaNguoiDung } from '@/services/khachHangService'
    import { getNhanVienByMaNguoiDung, getNhanViensByChuCuaHang } from '@/services/nhanVienService'
    import { createPayment, refundDeposit } from '@/services/thanhToanService'
    import { getXeMayByMaXeMay, updateXeMay } from '@/services/xeMayService'
    import L from 'leaflet'
    import 'leaflet-routing-machine'
    import 'leaflet/dist/leaflet.css'
    import { computed, defineComponent, nextTick, onMounted, ref } from 'vue'
    import { useRoute, useRouter } from 'vue-router'
    import ThongTinKhachHang from '../dungchung/ThongTinKhachHang.vue'

    export default defineComponent({
        name: 'ChiTietDonHangKhachHang',
        components: {
            PopupLoading,
            ThongTinKhachHang
        },
        setup() {
            const route = useRoute()
            const router = useRouter()
            const isError = ref(false)
            const messageError = ref<string>('')
            const donHang = ref<Record<string, null>>({})
            const nguoiDungKhachHang = ref<Record<string, null>>({})
            const khachHang = ref<Record<string, null>>({})
            const nguoiDungChuCuaHang = ref<Record<string, null>>({})
            const chuCuaHang = ref<Record<string, null>>({})
            const nguoiDungNhanVien = ref<Record<string, null>>({})
            const nhanVien = ref<Record<string, null>>({})
            const xeMays = ref<Record<string, string>[]>([])
            const chiTietDonHangs = ref<Record<string, string>[]>([])
            const nhanViens = ref<Record<string, unknown>[]>([])
            const isLoading = ref(false)
            const daTienHanhThanhToan = ref(false)
            const trangThaiDonHangParams = String(route.params.trangThaiDonHang)
            const tenNhanViens = ref<Record<string, string>>({})
            const isLoadingPage = ref(true)
            const selectedBankCode = ref<string>('')
            const startAddress = ref<string>('')
            const endAddress = ref<string>('')
            const banks = [
                {
                    code: 'ACB',
                    name: 'Ngân hàng TMCP Á Châu (ACB)',
                    logo: 'https://res.cloudinary.com/springboot-cloud/image/upload/v1745847207/acb_tqicsb.png'
                },
                {
                    code: 'Agribank',
                    name: 'Ngân hàng Nông nghiệp và Phát triển Nông thôn Việt Nam (Agribank)',
                    logo: 'https://res.cloudinary.com/springboot-cloud/image/upload/v1745850126/agribank_vyrrap.png'
                },
                {
                    code: 'BIDV',
                    name: 'Ngân hàng TMCP Đầu tư và Phát triển Việt Nam (BIDV)',
                    logo: 'https://res.cloudinary.com/springboot-cloud/image/upload/v1745847206/bidv_aug7zt.webp'
                },
                {
                    code: 'MBBank',
                    name: 'Ngân hàng TMCP Quân đội (MBBank)',
                    logo: 'https://res.cloudinary.com/springboot-cloud/image/upload/v1745850611/mbbank_uyfii1.png'
                },
                {
                    code: 'NCB',
                    name: 'Ngân hàng TMCP Quốc Dân (NCB)',
                    logo: 'https://res.cloudinary.com/springboot-cloud/image/upload/v1745848108/ncb_sdiwo4.webp'
                },
                {
                    code: 'OCB',
                    name: 'Ngân hàng TMCP Phương Đông (OCB)',
                    logo: 'https://res.cloudinary.com/springboot-cloud/image/upload/v1745849494/ocb_ybkl7w.png'
                },
                {
                    code: 'Sacombank',
                    name: 'Ngân hàng TMCP Sài Gòn Thương Tín (Sacombank)',
                    logo: 'https://res.cloudinary.com/springboot-cloud/image/upload/v1745850341/sacombank_wqkcxx.png'
                },
                {
                    code: 'SCB',
                    name: 'Ngân hàng TMCP Sài Gòn (SCB)',
                    logo: 'https://res.cloudinary.com/springboot-cloud/image/upload/v1745849788/scb_enxmui.png'
                },
                {
                    code: 'SHB',
                    name: 'Ngân hàng TMCP Sài Gòn - Hà Nội (SHB)',
                    logo: 'https://res.cloudinary.com/springboot-cloud/image/upload/v1745849623/shb_wxclhv.png'
                },
                {
                    code: 'TPBank',
                    name: 'Ngân hàng TMCP Tiên Phong (TPBank)',
                    logo: 'https://res.cloudinary.com/springboot-cloud/image/upload/v1745850620/tpbank_ykhdkj.png'
                },
                {
                    code: 'VIB',
                    name: 'Ngân hàng TMCP Quốc tế Việt Nam (VIB)',
                    logo: 'https://res.cloudinary.com/springboot-cloud/image/upload/v1745847207/vib_q50r1q.png'
                },
                {
                    code: 'Vietinbank',
                    name: 'Ngân hàng TMCP Công thương Việt Nam (Vietinbank)',
                    logo: 'https://res.cloudinary.com/springboot-cloud/image/upload/v1745850620/vietinbank_g4zazu.webp'
                },
                {
                    code: 'VPBank',
                    name: 'Ngân hàng TMCP Việt Nam Thịnh Vượng (VPBank)',
                    logo: 'https://res.cloudinary.com/springboot-cloud/image/upload/v1745850346/vpbank_kv0we0.webp'
                }
            ]

            const mapRef = ref<HTMLElement | null>(null)
            const loading = ref(false)
            const error = ref('')
            let map: L.Map
            // eslint-disable-next-line @typescript-eslint/no-explicit-any
            let routingControl: any

            const startIcon = L.divIcon({
                className: 'custom-icon',
                html: '<i class="fas fa-map-marker-alt" style="font-size: 30px;"></i>',
                iconSize: [30, 30],
                iconAnchor: [15, 30],
                popupAnchor: [0, -30]
            })

            const endIcon = L.divIcon({
                className: 'custom-icon',
                html: '<i class="fas fa-map-marker-alt" style="color:red; font-size: 30px;"></i>',
                iconSize: [30, 30],
                iconAnchor: [15, 30],
                popupAnchor: [0, -30]
            })

            const initMap = async () => {
                await nextTick()

                if (mapRef.value) {
                    map = L.map(mapRef.value).setView([16.0674, 108.2222], 14)
                    L.tileLayer('https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png').addTo(map)
                }
            }

            onMounted(async () => {
                await Promise.all([fetchDonHang(), fetchNhanViens()])
                isLoadingPage.value = false

                await nextTick()
                await initMap()

                if (trangThaiDonHangParams === 'Đang giao xe') {
                    startAddress.value = String(chuCuaHang.value.diaChiCuaHang)
                    endAddress.value = String(donHang.value.diaChiGiaoXe)

                    loading.value = true
                    error.value = ''

                    try {
                        const [start, end] = await Promise.all([
                            geocode(startAddress.value),
                            geocode(endAddress.value)
                        ])

                        L.marker([start.lat, start.lng], { icon: startIcon })
                            .addTo(map)
                            .bindTooltip('Cửa hàng', { permanent: true, direction: 'right' })

                        L.marker([end.lat, end.lng], { icon: endIcon })
                            .addTo(map)
                            .bindTooltip('Vị trí của bạn', { permanent: true, direction: 'right' })

                        if (routingControl) {
                            routingControl.setWaypoints([
                                L.latLng(start.lat, start.lng),
                                L.latLng(end.lat, end.lng)
                            ])
                        } else {
                            routingControl = L.Routing.control({
                                waypoints: [
                                    L.latLng(start.lat, start.lng),
                                    L.latLng(end.lat, end.lng)
                                ],
                                createMarker: () => null,
                                addWaypoints: false,
                                draggableWaypoints: false,
                                fitSelectedRoutes: true,
                                showAlternatives: false,
                                routeWhileDragging: false,
                                lineOptions: {
                                    styles: [{ color: 'blue', weight: 5 }],
                                    extendToWaypoints: false,
                                    missingRouteTolerance: 0
                                },
                                show: false
                                // eslint-disable-next-line @typescript-eslint/no-explicit-any
                            } as any)

                            routingControl.addTo(map)

                            routingControl.on('routesfound', () => {
                                const container = document.querySelector(
                                    '.leaflet-routing-container'
                                )
                                if (container) container.remove()
                            })
                        }

                        map.setView([start.lat, start.lng], 14)
                        // eslint-disable-next-line @typescript-eslint/no-explicit-any
                    } catch (err: any) {
                        error.value = err.message
                    }

                    loading.value = false
                }
            })

            const geocode = async (address: string) => {
                const fullAddress = address.includes('Đà Nẵng')
                    ? address
                    : `${address}, Đà Nẵng, Việt Nam`

                // Giới hạn vùng tìm kiếm trong Đà Nẵng
                const viewbox = '108.1,15.9,108.3,16.15'
                const url = `https://nominatim.openstreetmap.org/search?format=json&q=${encodeURIComponent(
                    fullAddress
                )}&viewbox=${viewbox}&bounded=1`

                const response = await fetch(url)
                const data = await response.json()
                if (!data.length)
                    throw new Error(`Không tìm thấy địa chỉ trong khu vực Đà Nẵng: ${address}`)

                return {
                    lat: parseFloat(data[0].lat),
                    lng: parseFloat(data[0].lon)
                }
            }

            const handleThanhToan = async () => {
                if (!selectedBankCode.value) {
                    alert('Vui lòng chọn ngân hàng để thanh toán!')
                    return
                }

                try {
                    const txnRefToanBo = `${donHang.value.maDonHang}-THANHTOANTOANBO`

                    const response = await createPayment(
                        Number(tongTien.value),
                        selectedBankCode.value,
                        String(txnRefToanBo)
                    )

                    if (response.status === 'Ok') {
                        window.location.href = response.url
                    } else {
                        alert('Tạo thanh toán thất bại: ' + response.message)
                    }
                } catch (error) {
                    console.error('Lỗi thanh toán:', error)
                    alert('Đã xảy ra lỗi trong quá trình thanh toán.')
                }
            }

            const handleThanhToanConLai = async () => {
                if (!selectedBankCode.value) {
                    alert('Vui lòng chọn ngân hàng để thanh toán!')
                    return
                }

                try {
                    const txnRefConLai = `${donHang.value.maDonHang}-THANHTOANCONLAI`
                    const tienConLai = Number(tongTien.value) * 0.8

                    const response = await createPayment(
                        tienConLai,
                        selectedBankCode.value,
                        String(txnRefConLai)
                    )

                    if (response.status === 'Ok') {
                        window.location.href = response.url
                    } else {
                        alert('Tạo thanh toán thất bại: ' + response.message)
                    }
                } catch (error) {
                    console.error('Lỗi thanh toán:', error)
                    alert('Đã xảy ra lỗi trong quá trình thanh toán.')
                }
            }

            const handleDatCoc = async () => {
                if (!selectedBankCode.value) {
                    alert('Vui lòng chọn ngân hàng để thanh toán!')
                    return
                }

                try {
                    const txnRefDatCoc = `${donHang.value.maDonHang}-DATCOC`
                    const tienDatCoc = Number(tongTien.value) * 0.2

                    const response = await createPayment(
                        tienDatCoc,
                        selectedBankCode.value,
                        String(txnRefDatCoc)
                    )

                    if (response.status === 'Ok') {
                        window.location.href = response.url
                    } else {
                        alert('Tạo thanh toán thất bại: ' + response.message)
                    }
                } catch (error) {
                    console.error('Lỗi thanh toán:', error)
                    alert('Đã xảy ra lỗi trong quá trình thanh toán.')
                }
            }

            const minGiaoXeDate = computed(() => {
                if (!xeMays.value.length) return undefined
                const tuNgay = new Date(xeMays.value[0].tuNgay)
                tuNgay.setDate(tuNgay.getDate() - 2)
                return tuNgay.toISOString().slice(0, 16)
            })

            const maxGiaoXeDate = computed(() => {
                if (!xeMays.value.length) return undefined
                const tuNgay = new Date(xeMays.value[0].tuNgay)
                return tuNgay.toISOString().slice(0, 16)
            })
            const minTraXeDate = computed(() => {
                if (!xeMays.value.length) return undefined
                const tuNgay = new Date(xeMays.value[0].tuNgay)
                tuNgay.setDate(tuNgay.getDate() + 2)
                tuNgay.setHours(0, 0, 0, 0)
                return tuNgay.toISOString().slice(0, 16)
            })

            const maxTraXeDate = computed(() => {
                if (!xeMays.value.length) return undefined
                const denNgay = new Date(xeMays.value[0].denNgay)
                denNgay.setDate(denNgay.getDate() + 2)
                return denNgay.toISOString().slice(0, 16)
            })

            const formatDate = (date: string) => {
                return useDate(date)
            }

            const formattedGiaThue = (giaThue: string) => {
                if (!giaThue) return '0'
                const numberGiaThue = Number(giaThue)
                return numberGiaThue.toLocaleString('vi-VN')
            }

            const fetchDonHang = async () => {
                const responseDonHang = await getDonHangByMaDonHang(String(route.params.maDonHang))
                donHang.value = responseDonHang.donHang
                khachHang.value = responseDonHang.khachHang
                nhanVien.value = responseDonHang.nhanVien
                chiTietDonHangs.value = responseDonHang.chiTietDonHangs

                const responseKhachHang = await getKhachHangByMaNguoiDung(
                    responseDonHang.khachHang.maKhachHang
                )
                nguoiDungKhachHang.value = responseKhachHang.nguoiDung

                if (responseDonHang.nhanVien) {
                    const responseNhanVien = await getNhanVienByMaNguoiDung(
                        responseDonHang.nhanVien.maNhanVien
                    )
                    nguoiDungNhanVien.value = responseNhanVien.nguoiDung
                }

                if (chiTietDonHangs.value.length > 0) {
                    const responseChuCuaHang = await getChuCuaHangByMaXeMay(
                        chiTietDonHangs.value[0].maXeMay
                    )
                    nguoiDungChuCuaHang.value = responseChuCuaHang.nguoiDung
                    chuCuaHang.value = responseChuCuaHang.chuCuaHang
                }

                const maXeMays = responseDonHang.chiTietDonHangs
                    .map((chiTiet: { maXeMay: string }) => chiTiet.maXeMay)
                    .filter((maXeMay: string) => maXeMay)

                const xeMayPromises = maXeMays.map((maXeMay: string) => getXeMayByMaXeMay(maXeMay))
                const xeMaysData = await Promise.all(xeMayPromises)

                xeMays.value = xeMaysData.map((xe) => {
                    const chiTiet = chiTietDonHangs.value.find(
                        (ct) => ct.maXeMay === xe.xeMay.maXeMay
                    )
                    return {
                        ...xe,
                        tuNgay: chiTiet ? chiTiet.tuNgay : 'NaN',
                        denNgay: chiTiet ? chiTiet.denNgay : 'NaN'
                    }
                })

                getTenNhanVien(String(donHang.value.maNhanVien))
            }

            const fetchNhanViens = async () => {
                const response = await getNhanViensByChuCuaHang(
                    String(chuCuaHang.value.maChuCuaHang)
                )
                nhanViens.value = response.map((item: { nguoiDung: object }) => item.nguoiDung)
            }

            const tongTien = computed(() => {
                return xeMays.value.reduce((total, item: Record<string, unknown>) => {
                    const xeMay = item.xeMay as Record<string, unknown>

                    const giaThue = xeMay.giaThue as number
                    const tuNgay = item.tuNgay
                    const denNgay = item.denNgay

                    const validTuNgay =
                        tuNgay &&
                        (typeof tuNgay === 'string' ||
                            typeof tuNgay === 'number' ||
                            tuNgay instanceof Date)
                    const validDenNgay =
                        denNgay &&
                        (typeof denNgay === 'string' ||
                            typeof denNgay === 'number' ||
                            denNgay instanceof Date)

                    const soNgayThue =
                        validTuNgay && validDenNgay
                            ? (new Date(denNgay).getTime() - new Date(tuNgay).getTime()) /
                              (1000 * 3600 * 24)
                            : 0

                    const tongTienXe = soNgayThue * giaThue

                    return total + tongTienXe
                }, 0)
            })

            const formattedTongTien = (gia: number) => {
                return new Intl.NumberFormat('vi-VN').format(gia)
            }

            const getTenNhanVien = async (maNhanVien: string) => {
                if (!tenNhanViens.value[maNhanVien]) {
                    const nhanVien = await getNhanVienByMaNguoiDung(maNhanVien)
                    tenNhanViens.value[maNhanVien] = nhanVien ? nhanVien.nguoiDung.hoVaTen : ''
                }
                return tenNhanViens.value[maNhanVien]
            }

            const handleTienHanhThanhToan = () => {
                daTienHanhThanhToan.value = true
            }

            const handleXacNhan = async () => {
                if (!donHang.value.lyDoHuy) {
                    isError.value = true
                    messageError.value = 'Vui lòng nhập đầy đủ các trường dữ liệu!'
                    setTimeout(() => {
                        isError.value = false
                        messageError.value = ''
                    }, 3000)
                    return
                }

                isLoading.value = true
                try {
                    if (donHang.value.trangThaiThanhToan === 'Đặt cọc') {
                        const refundResponse = await refundDeposit(
                            String(donHang.value.maDonHang),
                            tongTien.value * 0.2
                        )

                        if (refundResponse.status !== 200) {
                            throw new Error('Hoàn cọc thất bại')
                        }
                    }

                    const formData = new FormData()
                    formData.append('trangThaiDonHang', 'Đã hủy')
                    formData.append('lyDoHuy', donHang.value.lyDoHuy)

                    const response = await updateDonHang(String(donHang.value.maDonHang), formData)
                    if (response.success) {
                        const formData = new FormData()
                        formData.append('trangThaiHoatDong', 'Có sẵn')
                        for (let xe of xeMays.value) {
                            const responseXeMay = await updateXeMay(
                                (xe.xeMay as unknown as { maXeMay: string }).maXeMay,
                                formData
                            )
                            if (responseXeMay.success) {
                                await router.push({
                                    name: 'DanhSachDonHangKhachHangView',
                                    params: { trangThaiDonHang: 'Chờ xử lý' }
                                })
                            }
                        }
                    }
                } catch (error) {
                    console.error('Lỗi khi cập nhật: ', error)
                } finally {
                    isLoading.value = false
                }
            }

            return {
                mapRef,
                loading,
                error,
                isLoadingPage,
                handleTienHanhThanhToan,
                daTienHanhThanhToan,
                banks,
                selectedBankCode,
                handleThanhToan,
                handleDatCoc,
                handleThanhToanConLai,
                tenNhanViens,
                minTraXeDate,
                maxTraXeDate,
                minGiaoXeDate,
                maxGiaoXeDate,
                trangThaiDonHangParams,
                tongTien,
                formatDate,
                formattedGiaThue,
                formattedTongTien,
                isError,
                messageError,
                donHang,
                xeMays,
                nhanViens,
                nguoiDungKhachHang,
                khachHang,
                nguoiDungChuCuaHang,
                chuCuaHang,
                nhanVien,
                nguoiDungNhanVien,
                isLoading,
                chiTietDonHangs,
                handleXacNhan
            }
        }
    })
</script>

<style>
    .leaflet-routing-container {
        display: none !important;
    }

    .bank-button {
        margin-left: 10px;
        margin-top: 10px;
        width: 90%;
        padding: 10px;
        border: 1px solid #ccc;
        border-radius: 5px;
        background-color: #fff;
        cursor: pointer;
        transition: background-color 0.3s;
    }
    .bank-button:hover {
        background-color: #f0f0f0;
    }

    .title-user {
        font-size: 15px;
        color: #000000;
        opacity: 0.8;
        text-align: right;
    }
    .content-user {
        font-size: 16px;
        color: #000000;
        font-weight: 600;
    }
</style>
