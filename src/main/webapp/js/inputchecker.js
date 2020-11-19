/**
 * 사용자 입력항목에 대한 체크를 담당하는 자바스크립트 객체 <BR>
 *
 * @version	1.0, 2003-12-16
 * @author	namjinpark
 *@constructor
 */
function InputChecker() {
	this.isAscii		 	= isAscii_InputChecker;
	this.isValidEmail	 	= isValidEmail_InputChecker;
	this.isValidImageFile	= isValidImageFile_InputChecker;
	this.isValidXmlFile		= isValidXmlFile_InputChecker;
	this.isValidMusicFile	= isValidMusicFile_InputChecker;
	this.isValidVideoFile	= isValidVideoFile_InputChecker;
	this.isValidFileExt		= isValidFileExt_InputChecker;
	this.isNum				= isNum_InputChecker;
	this.isNumberRange		= isNumberRange_InputChecker;
	this.isNonSpecial		= isNonSpecial_InputChecker;
	this.isValidUrl			= isValidUrl_InputChecker;
	this.validate			= validate_InputChecker;
	this.isValidJumin		= isValidJumin_InputChecker;
	this.isValidTelNum		= isValidTelNum_InputChecker;
	this.isAllOrNothingValue= isAllOrNothingValue_InputChecker;
	this.checkvBusinessNo	=checkvBusinessNo_InputChecker;
	this.checkSlang			= checkSlang_InputChecker;
	this.getBytes = getBytes_InputChecker;
	this.getLimitedString = getLimitedString_InputChecker;
	this.isValidFileExt2 = isValidFileExt_InputChecker2;
	this.isValidId = isID_InputChecker;
	this.isValidPw = isPW_InputChecker;

}

function isValidFileExt_InputChecker2(lm_sFileName) {
	if(lm_sFileName != "")
	{
		var lm_iIndex = lm_sFileName.lastIndexOf('.');
		if(lm_iIndex == -1) return false;
		var lm_sExt = lm_sFileName.toLowerCase().substr(lm_iIndex+1);
		if(lm_sExt == "jsp" || lm_sExt == "js" || lm_sExt == "html" || lm_sExt == "htm") return false;
	}
	return true;
}

/**
 * 입력항목 체크의 대상이 되는 Form 상의 단위 Input 요소 및 그에 대한 속성을 나타낸다.<BR>
 * @param	pm_oObject		Form의 입력 객체
 * @param	pm_sName		입력객체의 표시이름
 * @param	pm_bRequired	필수유무
 * @param	pm_iMin			문자열 길이의 최소 값 (-1 이면 최소 값이 없음)
 * @param	pm_iMax			문자열 길이의 최대 값 (-1 이면 길이 제한 없음)
 * @param	pm_sDataType	데이터타입 ("string" : 문자열 "num" : 숫자 "email" : 이메일)
 * @version	1.0, 2003-12-17
 * @author	namjinpark
 *@constructor
 */
function CheckField(pm_oObject, pm_sName, pm_bRequired, pm_iMin, pm_iMax, pm_sDataType) {
	/** Form의 입력 객체 */
	this.im_oObject 	= pm_oObject;
	/** 입력객체의 표시이름 */
	this.im_sName 		= pm_sName;
	/** 필수유무 */
	this.im_bRequired 	= pm_bRequired;
	/** 문자열 길이의 최소 값 (-1 이면 최소 값이 없음) */
	this.im_iMin 		= pm_iMin;
	/** 문자열 길이의 최대 값 (-1 이면 길이 제한 없음) */
	this.im_iMax 		= pm_iMax;
	/** 데이터타입 */
	this.im_sDataType 	= pm_sDataType;
	/** 입력 객체 설정 값*/
	this.im_sValue		= "";
	/** Form의 입력 객체가 배열 요소인지의 변수 */
	this.im_bArray		= false;
	switch (pm_oObject.type){
		case "file":
		case "text":
		case "textarea":
		case "password":
		case "hidden":
			this.im_sValue = this.im_oObject.value;
			break;
		case "select-one":
		case "select-multiple":
			if(this.im_oObject.selectedIndex != -1)
				this.im_sValue = this.im_oObject.options[this.im_oObject.selectedIndex].value;
			break;
		case "checkbox":
		case "radio":
			this.im_sValue = (this.im_oObject.checked ? this.im_oObject.value : "");
			break;
		default:
			if(this.im_oObject.length) {
				this.im_bArray = true;
				switch (pm_oObject[0].type){
					case "file":
					case "text":
					case "textarea":
					case "password":
					case "hidden":
						this.im_sValue = this.im_oObject[0].value;
						break;
					case "checkbox":
					case "radio":
						for(var i=0; i < this.im_oObject.length; i++) {
							this.im_sValue = (this.im_oObject[i].checked ? this.im_oObject[i].value : "");
							if(this.im_sValue != "") break;
						}
				}
			}
	}
}

/**
 * 입력항목 체크의 대상에 대해서 입력체크를 수행한다. <BR>
 * @param	pm_oObject		Form의 입력 객체
 * @param	pm_sName		입력객체의 표시이름
 * @param	pm_bRequired	필수유무
 * @param	pm_iMin			문자열 길이의 최소 값 (-1 이면 최소 값이 없음)
 * @param	pm_iMax			문자열 길이의 최대 값 (-1 이면 길이 제한 없음)
 * @param	pm_sDataType	테이터타입 ("num", "currency", "email", "string", "ascii", "nonspecial", "html")
 *							"html" 인 경우는 focus 처리를 수행하지 않는다.
 *@return	입력체크에 문제가 없는 경우는 true 아니면 false를 반환한다.
 */
function validate_InputChecker(pm_oObject, pm_sName, pm_bRequired, pm_iMin, pm_iMax, pm_sDataType) {
	var lm_oCheckField = new CheckField(pm_oObject, pm_sName, pm_bRequired, pm_iMin, pm_iMax, pm_sDataType);
	return validate2_InputChecker(lm_oCheckField);
}

/**
 * 입력항목 체크의 대상에 대해서 입력체크를 수행한다. <BR>
 *@param	pm_oCheckField	입력항목 객체
 *@return	입력체크에 문제가 없는 경우는 true 아니면 false를 반환한다.
 */
function validate2_InputChecker(pm_oCheckField) {
	var lm_bFocus = true;
	if(pm_oCheckField.im_sDataType == "html") lm_bFocus=false;

	//필수입력항목 체크
	if(pm_oCheckField.im_bRequired){
		if(pm_oCheckField.im_sValue == "") {
			alert(pm_oCheckField.im_sName + "을(를) 입력해주세요");
			if(lm_bFocus) focusCheckField_InputChecker(pm_oCheckField);
			return false;
		}
	}

	//최소길이 체크
	if(pm_oCheckField.im_sValue.length != 0 && pm_oCheckField.im_iMin != -1){
		if(pm_oCheckField.im_sValue.length < pm_oCheckField.im_iMin) {
			alert(pm_oCheckField.im_sName + "은(는) 최소한 "+pm_oCheckField.im_iMin+"자 이상이어야만 합니다.");
			if(lm_bFocus) focusCheckField_InputChecker(pm_oCheckField);
			return false;
		}
	}

	//최대길이 체크
	if(pm_oCheckField.im_iMax != -1){
		if(pm_oCheckField.im_sValue.length > pm_oCheckField.im_iMax) {
			alert(pm_oCheckField.im_sName + "은(는) 최대 "+pm_oCheckField.im_iMax+"자를 넘을 수 없습니다.");
			if(lm_bFocus) focusCheckField_InputChecker(pm_oCheckField);
			return false;
		}
	}

	//데이터 타입에 따른 체크
	if(pm_oCheckField.im_sDataType == "num") {
		if(isNum_InputChecker(pm_oCheckField.im_sValue) == false) {
			alert("숫자만 입력 가능합니다.");
			if(lm_bFocus) focusCheckField_InputChecker(pm_oCheckField);
			return false;
		}
	}
	//Currency 체크
	if(pm_oCheckField.im_sDataType == "currency") {
		if(isCurrency_InputChecker(pm_oCheckField.im_sValue) == false) {
			alert("화폐형식이 틀립니다.");
			if(lm_bFocus) focusCheckField_InputChecker(pm_oCheckField);
			return false;
		}
	}
	//이메일 체크
	if(pm_oCheckField.im_sDataType == "email") {
		if(pm_oCheckField.im_sValue != "" && isValidEmail_InputChecker(pm_oCheckField.im_sValue) == false) {
			alert("이메일 형식이 틀립니다.");
			if(lm_bFocus) focusCheckField_InputChecker(pm_oCheckField);
			return false;
		}
	}
	//홈페이지 체크
	if(pm_oCheckField.im_sDataType == "homepage") {
		if(isValidUrl_InputChecker(pm_oCheckField.im_sValue) == false) {
			alert("홈페이지 형식이 틀립니다.");
			if(lm_bFocus) focusCheckField_InputChecker(pm_oCheckField);
			return false;
		}
	}
	//Ascii 문자만 입력 가능 (한글입력 불가)
	if(pm_oCheckField.im_sDataType == "ascii") {
		if(isAscii_InputChecker(pm_oCheckField.im_sValue) == false) {
			alert("한글을 입력할 수 없습니다.");
			if(lm_bFocus) focusCheckField_InputChecker(pm_oCheckField);
			return false;
		}
	}

	//특수문자 입력불가
	if(pm_oCheckField.im_sDataType == "nonspecial") {
		if(isNonSpecial_InputChecker(pm_oCheckField.im_sValue) == false) {
			alert("특수문자를 입력할 수 없습니다.");
			if(lm_bFocus) focusCheckField_InputChecker(pm_oCheckField);
			return false;
		}
	}

	//아이디(비밀번호)형식 체크 - 영문,숫자 모두 포함
	if(pm_oCheckField.im_sDataType == "id_all") {
		if(isID_InputChecker(pm_oCheckField.im_sValue,true) == false) {
			if(lm_bFocus) focusCheckField_InputChecker(pm_oCheckField);
			return false;
		}
	}
	//아이디(비밀번호)형식 체크 - 영문,숫자 상관없음
	if(pm_oCheckField.im_sDataType == "id_single") {
		if(isID_InputChecker(pm_oCheckField.im_sValue,false) == false) {
			if(lm_bFocus) focusCheckField_InputChecker(pm_oCheckField);
			return false;
		}
	}


	return true;
}





/**
 * 입력항목에 포커싱을 한다. <BR>
 *@param	pm_oCheckField	입력항목 객체
 */
function focusCheckField_InputChecker(pm_oCheckField) {
	if(pm_oCheckField.im_bArray) pm_oCheckField.im_oObject[0].focus();
	else pm_oCheckField.im_oObject.focus();
}

/**
 * 모든 문자열이 Ascii 문자열인지를 확인한다. <BR>
 * 한글입력을 금지하는 경우 사용할 수 있다.
 *
 * @param	pm_sStr		문자열
 * @return	모든 문자열이 Ascii 문자열이면 true 아니면 false
 */
function isAscii_InputChecker(pm_sStr) {
	var tlength = pm_sStr.length;
	for(var i=0; i < tlength; i++) {
		if(pm_sStr.charCodeAt(i) > 256) {
			return false;
		}
	}
	return true;
}

/**
 * 모든 문자열이 아이디(비밀번호) 형식에 맞는 문자열인지를 확인한다. <BR>
 * 한글입력,특수기호을 금지하는 경우 사용할 수 있다.
 *
 * @param	pm_sStr		문자열
 * @param pm_bChk   true=영문,숫자가 모두포함 형식, false=상관없음
 * @return	모든 문자열이 Ascii 문자열이면 true 아니면 false
 */
function isID_InputChecker(pm_sStr, pm_bChk) {
	var eng_valid = "abcdefghijklmnopqrstuvwxyz";
	var num_valid = "0123456789";
	var eng_cnt = 0;
	var num_cnt = 0;
	var tlength = 0;

	pm_sStr = pm_sStr.toLowerCase();	// 소문자로 변환
	tlength = pm_sStr.length;

	for (var i=0; i<tlength; i++) {
		var tmp = pm_sStr.charAt(i);
		if(eng_valid.indexOf(tmp) == "-1" && num_valid.indexOf(tmp) == "-1") {
			alert("아이디는 영문,숫자만 입력할 수 있습니다.");
			return false;
		}
		else if (pm_bChk) {
			if (eng_valid.indexOf(tmp) != "-1") eng_cnt++;
			else if (num_valid.indexOf(tmp) != "-1") num_cnt++;
		}
	}

	if (pm_bChk && (eng_cnt==0 || num_cnt==0)) {

		alert("비밀번호는 영문,숫자 모두 포함되야 합니다.");
		return false;
	}

	return true;
}


/**
 * 올바른 URL 문자열인지를 확인한다. <BR>
 *
 * @param	lm_sStr		문자열
 * @return	올바른 URL 문자열이면 true 아니면 false
 */
function isValidUrl_InputChecker(lm_sStr) {
	var lm_sReturn = lm_sStr.match(/\w{2,}:\/{2}([-\w]+\.)+\w+\S*/g);
	if(lm_sReturn != null) return true;
	return false;
}

/**
 * E-Mail 올바른 이메일 문자열인지를 반환한다.
 *
 * @param	lm_sStr		문자열
 * @return	올바른 이메일 문자열이면 true 아니면 false
 */
function isValidEmail_InputChecker(lm_sStr) {
	if(lm_sStr == null) return false;
	var lm_iIndex = lm_sStr.indexOf('@');
	if(lm_iIndex == -1 || lm_iIndex == 0 || lm_iIndex == lm_sStr.length-1) return false;
	return true;
}

/**
 * 적절한 이미지 파일 이름인지를 반환한다. (gif, jpg, bmp)
 *
 * @param	lm_sFileName	파일이름
 * @return	확장자가 적절한 이미지 파일이면 true 아니면 false
 */
function isValidImageFile_InputChecker(lm_sFileName) {
	if(lm_sFileName == null) return false;
	var lm_iIndex = lm_sFileName.lastIndexOf('.');
	if(lm_iIndex == -1) return false;
	var lm_sExt = lm_sFileName.toLowerCase().substr(lm_iIndex+1);
	if(lm_sExt == "gif" || lm_sExt == "jpg" || lm_sExt == "jpeg" || lm_sExt == "bmp") return true;
	return false;
}

/**
 * 허용된 파일 확장자 인지를 반환한다. (gif, jpg, bmp)
 *
 * @param	lm_sFileName	파일이름
 * @param	pm_oExtArray	허용된 파일 확장자 배열
 * @return	확장자가 적절한 이미지 파일이면 true 아니면 false
 */
function isValidFileExt_InputChecker(lm_sFileName, pm_oExtArray) {
	if(lm_sFileName == null) return false;
	var lm_iIndex = lm_sFileName.lastIndexOf('.');
	if(lm_iIndex == -1) return false;
	var lm_sExt = lm_sFileName.toLowerCase().substr(lm_iIndex+1);
	var lm_bFound = false;
	for(var i=0; i < pm_oExtArray.length; i++) {
		if(pm_oExtArray[i] == lm_sExt) return true;
	}
	return false;
}

/**
 * 적절한 XML 파일 이름인지를 반환한다. (xml)
 *
 * @param	lm_sFileName	파일이름
 * @return	확장자가 적절한 XML 파일이면 true 아니면 false
 */
function isValidXmlFile_InputChecker(lm_sFileName) {
	if(lm_sFileName == null) return false;
	var lm_iIndex = lm_sFileName.lastIndexOf('.');
	if(lm_iIndex == -1) return false;
	var lm_sExt = lm_sFileName.toLowerCase().substr(lm_iIndex+1);
	if(lm_sExt == "xml") return true;
	return false;
}

/**
 * 적절한 음악 파일 이름인지를 반환한다. (wma, mp3)
 *
 * @param	lm_sFileName	파일이름
 * @return	확장자가 적절한 이미지 파일이면 true 아니면 false
 */
function isValidMusicFile_InputChecker(lm_sFileName) {
	if(lm_sFileName == null) return false;
	var lm_iIndex = lm_sFileName.lastIndexOf('.');
	if(lm_iIndex == -1) return false;
	var lm_sExt = lm_sFileName.toLowerCase().substr(lm_iIndex+1);
	if(lm_sExt == "wma" || lm_sExt == "mp3") return true;
	return false;
}

/**
 * 적절한 동영상 파일 이름인지를 반환한다. (avi, asf, mpeg)
 *
 * @param	lm_sFileName	파일이름
 * @return	확장자가 적절한 이미지 파일이면 true 아니면 false
 */
function isValidVideoFile_InputChecker(lm_sFileName) {
	if(lm_sFileName == null) return false;
	var lm_iIndex = lm_sFileName.lastIndexOf('.');
	if(lm_iIndex == -1) return false;
	var lm_sExt = lm_sFileName.toLowerCase().substr(lm_iIndex+1);
	if(lm_sExt == "avi" || lm_sExt == "asf" || lm_sExt == "mpeg") return true;
	return false;
}

/**
 * 숫자값이 low 보다 크거나 같고 high 보다 작거나 같은지 검사한다.
 *
 * @param	lm_sFileName	파일이름
 * @return	확장자가 적절한 이미지 파일이면 true 아니면 false
 */
function isNumberRange_InputChecker(val, low, high){
    if(this.isNum(val) == false) return false;
    var lm_iNum = parseInt(val);
    if ( lm_iNum >= low && lm_iNum <= high){
        return true;
    } else {
        return false;
    }
}

/**
 * 입력값이 숫자인지를 확인한다. <BR>
 *@param	name	확인하려는 문자열
 *@return  입력값이 전부 숫자일 경우 true return, 숫자가 아닌 값이 포함되었을 경우 false return
 */
function isNum_InputChecker(name) {
    var ch = "\0";
	var flag = true;

    for (var i = 0, ch = name.charAt(i);
        (i < name.length) && (flag); ch = name.charAt(++i)) {
        if ((ch >= '0') && (ch <= '9'))
        	;
        else if( (i == 0)&&(ch == '-'))
        	;
        else
            flag = false;
    }
    return flag;
}

/**
 * 입력값이 화폐인지를 확인한다. <BR>
 *@param	name	확인하려는 문자열
 *@return  입력값이 숫자, ",", "." 인 경우 true return, 그 외의 문자를 포함하는 하는 false
 */
function isCurrency_InputChecker(name) {
    var ch = "\0";
	var flag = true;

    for (var i = 0, ch = name.charAt(i);
        (i < name.length) && (flag); ch = name.charAt(++i)) {
        if ((ch >= '0') && (ch <= '9'))
        	;
        else if( (i == 0)&&(ch == '-'))
        	;
        else if(ch == ',' || ch == '.')
        	;
        else
            flag = false;
    }
    return flag;
}

/**
 * 문자열 중에 특수문자가 포함되었는지를 파악한다.
 *@param	name 	문자열
 *@return	입력값에 특수 문자가 하나도 포함되지 않았을 경우 true return
 */
function isNonSpecial_InputChecker(name) {

    var ch = "\0";

    for (var i = 0, ch = name.charAt(i);
        i <name.length; ch = name.charAt(++i)) {
        if ( ch == ' ' || ch == '~' || ch == '`' || ch == '\\'||
             ch == '-' || ch == '_' || ch == '|' || ch == '+' ||
             ch == '=' || ch == ',' || ch == '.' || ch == '/' ||
             ch == '<' || ch == '>' || ch == '?' || ch == '!' ||
             ch == '@' || ch == '#' || ch == '$' || ch == '%' ||
             ch == '^' || ch == '&' || ch == '*' || ch == '(' ||
             ch == ')' || ch == '\"' || ch == '[' || ch == ']' ||
             ch == ':' || ch == ';' || ch == '\'' || ch == '{' ||
             ch == '}' ) {
            return false;
        }
    }
    return true;
}

/**
   주민등록번호가 유효한지 체크한다.
   @param ssnumber1		주민번호 앞자리
   @param ssnumber2		주민번호 뒤자리
   @return boolean		유효한 주민번호이면 true, 그렇지 않으면 false
*/
function isValidJumin_InputChecker(ssnumber1, ssnumber2) {
     var a = Array(6);
     var b = Array(7);

     for (var i=0; i<6; i++)
         a[i] = parseInt(ssnumber1.charAt(i));

     for (var j=0; j<7; j++)
         b[j] = parseInt(ssnumber2.charAt(j));

     var ssntot = (a[0]*2)+(a[1]*3)+(a[2]*4)+(a[3]*5)+(a[4]*6)+(a[5]*7) + (b[0]*8)+(b[1]*9)+(b[2]*2)+(b[3]*3)+(b[4]*4)+(b[5]*5);
     var ssnave = 11 - (ssntot%11);

     if (ssnave == 11) ssnave = 1;
     else if(ssnave == 10) ssnave = 0;

     if (b[6] == ssnave) return true;
     else return false;
}

/**
   유효한 전화번호인지를 체크한다.
   @param tel1		전화번호(지역번호)
   @param tel2		전화번호(국번)
   @param tel3		전화번호(나머지)
   @return boolean		유효한 전화번호이면 true, 그렇지 않으면 false
*/
function isValidTelNum_InputChecker(tel1, tel2, tel3) {
	//모두 값이 없는 경우는 true를 반환한다.
	if(tel1 == "" && tel2 == "" && tel3 == "") return true;
	if(tel1 == "" && (tel2 + tel3) != "") return false;
	if(tel2 == "" && (tel1 + tel3) != "") return false;
	if(tel3 == "" && (tel1 + tel2) != "") return false;
	return true;
}

/**
 * 전화번호, 카드번호와 같이 일련의 text 값들이 모두 값을 갖거나 갖지 않아야 하는
 * 경우 체크를 수행한다. <BR>
 * validate function 을 먼저 수행하여 type 및 자리수 체크를 하고 이 function 을
 * 호출할 것
 * @param	n 개의 값들(n > 1) 내부적으로 arguments에 의해서 처리된다.
 * @return 모두 값을 갖거나 모두 값을 갖지 않는 경우에만 true를 반환한다.
 */
function isAllOrNothingValue_InputChecker() {
	var vArgs = isAllOrNothingValue_InputChecker.arguments;
	if(vArgs.length == 0) return true;
	//Nothing Check
	if(vArgs[0] == "") {
		for(var i=1; i < vArgs.length; i++) {
			if(vArgs[i] != "") return false;
		}//for
	} //if
	//All Check
	if(vArgs[0] != "") {
		for(var i=1; i < vArgs.length; i++) {
			if(vArgs[i] == "") return false;
		}//for
	} //if
	return true;
}



/**
 * 10자리의 사업자번호를 체크하여 올바른 경우는 true를 올바르지 않은 경우는 false
 * 를 반환한다.
 * @param	vBusinessNo	사업자등록번호
 */
function checkvBusinessNo_InputChecker(vBusinessNo) {
	var i=0;
	var sum=0;
	var li_y;
	var epno_chk;
	var li_epno = new Array(10);
	var li_chkValue = [1,3,7,1,3,7,1,3,5];

	if (vBusinessNo.length != 10) {
		return false;
	}

	for (i=0;i<10;i++ ) {
		li_epno[i] = vBusinessNo.substring(i,i+1);
	}

	for (i=0;i<9;i++) {
		sum += li_epno[i] * li_chkValue[i];
	}

	sum = sum + ((li_epno[8] * 5)  / 10);
	li_y = Math.floor(sum % 10);

	if (li_y == 0) epno_chk = 0;
	else epno_chk = 10 - li_y;

	if (epno_chk == li_epno[9])
		return true;
	else
		return false;
}

/**
 * 욕 필터링 및 스크립트 코드 방지
 * 발견시 false를 반환한다.
 * @param	vTxt	텍스트
 */
function checkSlang_InputChecker( vTxt ){
	var YokList = new Array("<style","<script","javascript:","<body","<div","<meta","<title","<html","<head","<div","<body","<layer","cunt","shit","fuck","asshole","suck","pussy","cock","penis","sux","dick","bitch","damn","sucker","fucker","개새끼","개자식","개쉐이","이새끼","저새끼","그새끼","씨벌","시팔","시발","시벌","씨부랄","씨부럴","씹","십새끼","십새","십쉐이","18놈","18넘","18년","좆","미친놈","미친넘","미친년","미친새끼","오입","염병","엠병","지랄","젖밥","개?q","개쉐","개뿔","개새","개세","개새끼","개허접","딩시","딩신","돌아이","똘아이","똥","미췬","미친","미틴","븅신","병쉰","빙시","빙신","병신","뱅신","빠가","새.끼","십새","십세","씹새","씹세","씨팍","섹스","시댕","씨댕","씨파","시댕","씨팔","씨발","씌파","씨봉","씨방","씨방새","씨방세","씨방","새끼","세끼","쉬펄","쉬벌","쒸펄","쒸벌","쒸뻘","쉬뻘","애자","주접떨","지랄","지롤","지럴","좃까","좃같은","주둥아리","주둥이","凸","젖 밥","개 ?q","개 쉐","개 뿔","개 새","개 세","딩 시","딩 신","미 친","미 췬","미 틴","븅 신","병 쉰","빙 시","빙 신","병 신","뱅 신","빠 가","새.끼","새 끼","십 새","십 세","씹 새","씹 세","씨 팍","섹 스","시 댕","씨 댕","씨 파","시 댕","씨 팔","씨 발","씌 파","씨 봉","씨 방","새 끼","세 끼","쉬 펄","쉬 벌","쒸 펄","쒸 벌","쒸 뻘","쉬 뻘","애 자","지 랄","지 롤","지 럴","좃 까","쌍뇬","씨바랄","조까","씨불","개시키","개섀끼","쓰발","새꺄")

	var Tmp;
	for( i = 0; i < YokList.length; i++ ){
		Tmp = vTxt.value.toLowerCase().indexOf( YokList[i] );
		if( Tmp >= 0 ){
			alert( YokList[i] + " 는(은) 사용하실 수 없는 단어입니다." );
			return false;
		}
	}
	return true;
}

/**
 * 데이터를 받아서 bytes 크기를 반환
 *
 * @param input 바이트 크기를 조회할 문자열
 * @return 바이트 크기
 */
function getBytes_InputChecker(input) {
	var nbytes = 0;
	for (i=0; i<input.length; i++) {
    var ch = input.charAt(i);

    if(escape(ch).length > 4) {
    	nbytes += 2;
    }
    else if (ch == '\n') {
	    if (input.charAt(i-1) != '\r') {
	    	nbytes += 1;
	    }
    }
    else if (ch == '<' || ch == '>') {
    	nbytes += 4;
    }
    else {
    	nbytes += 1;
    }
	}
	return nbytes;
}

/**
 * 데이터를 받아서 정해진 크기의 바이트만큼만 반환
 *
 * @param Input 바이트 크기를 조회할 문자열, 제한 크기
 * @return 정해진 크기의 문자열
 */
function getLimitedString_InputChecker(input, limit) {
	var inc = 0;
	var nbytes = 0;
	var msg = "";
	var msglen = input.length;

	for (i=0; i < msglen; i++) {
		var ch = input.charAt(i);

		if (escape(ch).length > 4) {
			inc = 2;
		}
		else if (ch == '\n') {
			if (input.charAt(i-1) != '\r') {
				inc = 1;
			}
		}
		else if (ch == '<' || ch == '>') {
			inc = 4;
		}
		else {
			inc = 1;
		}

		if ((nbytes + inc) > limit) {
			break;
		}

		nbytes += inc;
		msg += ch;
	}
	return msg;
}
/**  20141212 lsg
* 모든 문자열이 비밀번호 형식에 맞는 문자열인지를 확인한다. <BR>
*
* @param	pw_sStr		문자열
* @param pm_bChk   true=영문,숫자가 모두포함 형식, false=상관없음
* @return	모든 문자열이 Ascii 문자열이면 true 아니면 false
*/
function isPW_InputChecker(paramObjt) {
	var minimum = 9;
	var maximun = 12;

	var spacial_char = /[~!@\#$%<>^&*\()\-=+_\']/gi;
	var eng_check1 = /[a-z]/g;
	var eng_check2 = /[A-Z]/g;
	var num_check = /[0-9]/g;

	// 글자 갯수 제한
	var msg = chkPwLength(paramObjt);
	if(msg == "") msg = "안전한 비밀번호 입니다.";

	function chkPwLength(paramObj) {
		var msg = "";
		var paramCnt = paramObj.value.length;
		if(paramCnt < minimum) {
			msg = "최소 암호 글자수는 <b>" + minimum + "</b> 입니다.";
		} else if(paramCnt > maximun) {
			msg = "최대 암호 글자수는 <b>" + maximun + "</b> 입니다.";
		} else if(!eng_check2.test(paramObj.value)) {
			msg = "비밀번호는 영문(대문자)가 포함되어야 합니다.";
		} else if(!eng_check1.test(paramObj.value)) {
			msg = "비밀번호는 영문(소문자)가 포함되어야 합니다.";
		} else if(!spacial_char.test(paramObj.value)) {
			msg = "비밀번호는 특수문자가 포함되어야 합니다.";
		} else if(!num_check.test(paramObj.value)) {
			msg = "비밀번호는 숫자가 포함되어야 합니다.";
		}
		else {
			msg = chkPwNumber(paramObj);
		}
		return msg;
	}

	// 암호 유용성 검사
	function chkPwNumber(paramObj) {
		var msg = "";
		var paramTxt = paramObj.value;
		var chk_num = paramTxt.search(/[0-9]/g);
		var chk_eng1 = paramTxt.search(/[a-z]/ig);
		var chk_eng2 = paramTxt.search(/[A-Z]/ig);
		var chk_spe = paramTxt.search(/[!@#$%^&*]/g);

		if(chk_num < 1 & chk_eng1  < 1 & chk_eng2  < 1 & chk_spe < 1) {
			msg = "비밀번호는 영어(대/소문자), 숫자, 특수문자의 조합으로 9~12자리로 입력해주세요.!!!";
		} else {
			msg = chkPwContinuity(paramObj);
		}

		return msg;
	}

	// 암호 연속성 검사 및 동일 문자
	function chkPwContinuity(paramObj) {
		var msg = "";
		var SamePass_0 = 0; //동일문자 카운트
		var SamePass_1_str = 0; //연속성(+) 카운드(문자)
		var SamePass_2_str = 0; //연속성(-) 카운드(문자)
		var SamePass_1_num = 0; //연속성(+) 카운드(숫자)
		var SamePass_2_num = 0; //연속성(-) 카운드(숫자)

		var chr_pass_0;
		var chr_pass_1;
		var chr_pass_2;

		for(var i=0; i < paramObj.value.length; i++) {
			chr_pass_0 = paramObj.value.charAt(i);
			chr_pass_1 = paramObj.value.charAt(i+1);

			//동일문자 카운트
			if(chr_pass_0 == chr_pass_1)
			{
				SamePass_0 = SamePass_0 + 1
			}


			chr_pass_2 = paramObj.value.charAt(i+2);

			if(chr_pass_0.charCodeAt(0) >= 48 && chr_pass_0.charCodeAt(0) <= 57) {
				//숫자
				//연속성(+) 카운드
				if(chr_pass_0.charCodeAt(0) - chr_pass_1.charCodeAt(0) == 1 && chr_pass_1.charCodeAt(0) - chr_pass_2.charCodeAt(0) == 1)
				{
					SamePass_1_num = SamePass_1_num + 1
				}

				//연속성(-) 카운드
				if(chr_pass_0.charCodeAt(0) - chr_pass_1.charCodeAt(0) == -1 && chr_pass_1.charCodeAt(0) - chr_pass_2.charCodeAt(0) == -1)
				{
					SamePass_2_num = SamePass_2_num + 1
				}
			} else {
				//문자
				//연속성(+) 카운드
				if(chr_pass_0.charCodeAt(0) - chr_pass_1.charCodeAt(0) == 1 && chr_pass_1.charCodeAt(0) - chr_pass_2.charCodeAt(0) == 1)
				{
					SamePass_1_str = SamePass_1_str + 1
				}

				//연속성(-) 카운드
				if(chr_pass_0.charCodeAt(0) - chr_pass_1.charCodeAt(0) == -1 && chr_pass_1.charCodeAt(0) - chr_pass_2.charCodeAt(0) == -1)
				{
					SamePass_2_str = SamePass_2_str + 1
				}
			}
		}

		if(SamePass_0 > 1) {
			msg = "동일숫자 및 문자를 3번 이상 사용하면 비밀번호가 안전하지 못합니다.(ex : aaa, bbb, 111)";
		}

		if(SamePass_1_str > 1 || SamePass_2_str > 1 || SamePass_1_num > 1 || SamePass_2_num > 1)
		{
			msg = "연속된 문자열(123 또는 321, abc, cba 등)을\n 3자 이상 사용 할 수 없습니다.";
		}

		return msg;
	}
	return msg;
}
