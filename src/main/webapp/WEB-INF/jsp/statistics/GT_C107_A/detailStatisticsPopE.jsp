<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/common/taglibs.jsp" %>

<ul class="tab">
    <li><a href="#this" data-target="total">전체현황</a></li>
    <li><a href="#this" data-target="where">조건별 현황</a></li>
    <li class="current"><a href="#this" data-target="detail">상세내역</a></li>
    <li><a href="#this" data-target="cross">교차로기준 현황</a></li>
</ul>

<form  action="${context}/statistics/selectDetailFcltsExcelDown.do" id="frm" name="frm" method="post">
    <input type="hidden" id="facilityId" name="facilityId" value="<c:out value='${params.facilityId}'/>" />
    <input type="hidden" id="facilityName" name="facilityName" value="컬러포장" />
    <input type="hidden" id="guCde" name="guCde" value="" />
    <input type="hidden" id="dongCde" name="dongCde" value="" />
    <input type="hidden" id="policeCde" name="policeCde" value="" />
    <input type="hidden" id="statGbn" name="statGbn" value="" />
    <input type="hidden" id="arrHeader" name="arrHeader" value="" />

    <div class="contBox_tp3" style="width: 761px;">
        <div class="area_wrap">
            <div class="area_l w50p pd_r10">
                <div id="mGu" class="board"></div>
                <div id="mPolice" class="board"></div>
            </div>
            <div class="area_r w50p pd_r10">
                <div id="mDong" class="board"></div>
                <div id="mClr" class="board"></div>
            </div>
        </div>
        <div class="search_wrap_tp3 mg_t10" style="width: 710px; padding: 15px 0 15px 138px;">
            <div class="inp_wrap">
                <div class="inp tit" style="width: 100px;">설치일</div>
                <div class="inp">
                    <input type="text" id="stYmd" name="stYmd" class="ico_calendar datapicker_open_std" style="width:120px;">
                </div>
                <div class="inp">~</div>
                <div class="inp">
                    <input type="text" id="enYmd" name="enYmd" class="ico_calendar datapicker_open_end" style="width:120px;">
                </div>
            </div>
        </div>
        <div class="btn_wrap tp3">
            <a href="#this" class="btn sml_tp3 blue" id="search">검색</a>
            <a href="#this" class="btn btn_excel" id="excelDown" style="width: 105px;">엑셀 다운로드</a>
        </div>
    </div>
</form>

<div class="mg_t15 detailBoard">
    <div class="board_tp1 mg_t40" style="overflow-y: auto;">
        <div class="cnt" style="text-align: left;"></div>
    </div>
    <div class="board_pager_wrap"></div>
</div>

<%--<h3 class="h3_tp4 mg_t15" style="font-weight:400">구별 통계</h3>--%>

<%--<div class="mg_t15 GuResult"></div>--%>

<%--<h3 class="h3_tp4 mg_t25" style="font-weight:400">경찰서별 통계</h3>--%>

<%--<div class="mg_t15 PoliceResult"></div>--%>

<%--<h3 class="h3_tp4 mg_t25" style="font-weight:400">동별 통계</h3>--%>

<%--<div class="mg_t15 mg_b30 DongResult"></div>--%>

<script>

    var searchResultList = null;
    var  mGu = null;
    var  mDong = null;
    var  mPolice = null;
    var  mClr = null;

    $(function() {

        $("ul.tab > li > a").on("click",function (evt) {
            $("#statistics").load("${context}/statistics/"+$(this).attr("data-target")+"StatisticsPopE.do?facilityId="+"${params.facilityId}");
        });

        $("ul.tab > li > a").css({'border-bottom' : '0px'});

        setDatePicker();

        mGu = new MultiSelectBoard("#mGu", {
            url : "<c:url value='/common/util/code/combobox.json?codegroup=guCde'/>",
            type : "get",
            caption : "*시군구",
            allSelect : false,
            colNames : [
                {width : "210px]"},
                {width : "auto"}
            ]
        });

        mDong =  new MultiSelectBoard("#mDong", {
            url : "<c:url value='/common/util/code/combobox.json?codegroup=emdCde'/>",
            type : "get",
            caption : "*읍면동",
            allSelect : false,
            colNames : [
                {width : "210px]"},
                {width : "auto"}
            ]
        });

        mPolice =  new MultiSelectBoard("#mPolice", {
            url : "<c:url value='/common/util/code/combobox.json?codegroup=peCde'/>",
            type : "get",
            caption : "*경찰서",
            allSelect : false,
            colNames : [
                {width : "210px]"},
                {width : "auto"}
            ]
        });

        mClr =  new MultiSelectBoard("#mClr", {
            url : "<c:url value='/common/util/code/combobox.json?codegroup=commonCode&codeId=CLR&refCd=C107_A'/>",
            type : "get",
            caption : "*색상",
            allSelect : false,
            colNames : [
                {width : "210px]"},
                {width : "auto"}
            ]
        });

        $(".btn_excel").click(function() {
            $("#guCde").val(mGu.getSelectVal());
            $("#dongCde").val(mDong.getSelectVal());
            $("#policeCde").val(mPolice.getSelectVal());
            $("#statGbn").val(mClr.getSelectVal());
            $("#arrHeader").val("번호, 관리번호, 경찰서명, 구명, 동명, 지번, 교차로, 색상, 폭원, 길이, 설치일자");
            $("#frm").submit();
        });

        $("#search").click(function() {
            searchResultList.reloadList({
                facilityId : "${params.facilityId}",
                guCde:mGu.getSelectVal(),
                dongCde:mDong.getSelectVal(),
                policeCde:mPolice.getSelectVal(),
                statGbn : mClr.getSelectVal(),
                stYmd : $("#stYmd").val(),
                enYmd : $("#enYmd").val()
            });
        });

        searchResultList = new SearchResultList(".detailBoard", {
            url					: "${context}/statistics/selectDetailFcltsList.json",
            caption : "상세내역",
            reqData			: { facilityId : "${params.facilityId}"},
            mouseCursor : "default",
            mouseHover	: false,
            tableClass : "tbl_col_tp6",
            colNames		: [
                {name : "번호", width : "50px"},
                {name : "관리번호", width : "100px"},
                {name : "경찰서명", width : "100px"},
                {name : "구명", width : "100px"},
                {name : "동명", width : "100px"},
                {name : "지번", width : "100px"},
                {name : "교차로", width : "120px"},
                {name : "색상", width : "120px"},
                {name : "폭원", width : "120px"},
                {name : "길이", width : "120px"},
                {name : "설치일자", width : "120px"}
            ],
            colModel		: [
                {name : "num"},
                {name : "mgrnu"},
                {name : "peNam"},
                {name : "guNam"},
                {name : "dongNam"},
                {name : "jibun"},
                {name : "cssNam"},
                {name : "clrNam"},
                {name : "clrWid"},
                {name : "clrLen"},
                {name : "esbYmd"}
            ]
        });
    });
</script>
