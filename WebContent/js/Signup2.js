$(document).ready(function() {
    // 처음 로드 시 모든 input을 회색 배경으로 설정하고 비활성화
    $('input[name="email"]').css('background-color', '#e0e0e0').prop('disabled', true);
    $('input[name="password"]').css('background-color', '#e0e0e0').prop('disabled', true);
    $('input[name="confirmPassword"]').css('background-color', '#e0e0e0').prop('disabled', true);

    $('input[name="name"]').on('input', function() {
        if (validateName($(this).val())) {
            $('input[name="email"]').css('background-color', 'white').prop('disabled', false); // 이름 입력 시 이메일 input 활성화
        } else {
            $('input[name="email"]').css('background-color', '#e0e0e0').prop('disabled', true); // 이름 미입력 시 이메일 input 비활성화
        }
        checkFormValidity();
    });

    $('input[name="email"]').on('input', function() {
        if (validateEmail($(this).val())) {
            $('input[name="password"]').css('background-color', 'white').prop('disabled', false); // 이메일 입력 시 비밀번호 input 활성화
            $('input[name="confirmPassword"]').css('background-color', 'white').prop('disabled', false); // 이메일 입력 시 비밀번호 확인 input 활성화
        } else {
            $('input[name="password"]').css('background-color', '#e0e0e0').prop('disabled', true); // 이메일 미입력 시 비밀번호 input 비활성화
            $('input[name="confirmPassword"]').css('background-color', '#e0e0e0').prop('disabled', true); // 이메일 미입력 시 비밀번호 확인 input 비활성화
        }
        checkFormValidity();
    });
 
    $('input[name="password"], input[name="confirmPassword"]').on('input', function() {
        checkFormValidity();
    });
    
    function validateEmail(email) {
        var emailPattern = /^[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,6}$/;
        return emailPattern.test(email);
    }

    function validatePassword(password) {
         // 특수문자에 '.'(온점)도 포함하도록 정규식 수정
        var passwordPattern = /^(?=.*[a-zA-Z])(?=.*\d)(?=.*[@$!%*?&.])[A-Za-z\d@$!%*?&.]{8,20}$/;
        return passwordPattern.test(password);
    }

    function validateName(name) {
        return name.trim().length > 0;
    }

    function validateConfirmPassword(password, confirmPassword) {
        return password === confirmPassword;
    }

    function checkFormValidity() {
        var name = $('input[name="name"]').val();
        var email = $('input[name="email"]').val();
        var password = $('input[name="password"]').val();
        var confirmPassword = $('input[name="confirmPassword"]').val();

        var isNameValid = validateName(name);
        var isEmailValid = validateEmail(email);
        var isPasswordValid = validatePassword(password);
        var isConfirmPasswordValid = validateConfirmPassword(password, confirmPassword);

        if (isNameValid && isEmailValid && isPasswordValid && isConfirmPasswordValid) {
            $('#div_next').prop('disabled', false); // 버튼 활성화
        } else {
            $('#div_next').prop('disabled', true); // 버튼 비활성화
        }
    }

    $('input[name="name"]').on('input', function() {
        if (!validateName($(this).val())) {
            $('#nameError').text('이름을 입력해 주세요.'); // 오류 메시지 출력
        } else {
            $('#nameError').text(''); // 오류 메시지 제거
        }
        checkFormValidity();
    });

    $('input[name="email"]').on('input', function() {
        if (!validateEmail($(this).val())) {
            $('#emailError').text('이메일 형태로 입력해 주세요.'); // 오류 메시지 출력
        } else {
            $('#emailError').text(''); // 오류 메시지 제거
        }
        checkFormValidity();
    });

    $('input[name="password"]').on('input', function() {
        if (!validatePassword($(this).val())) {
            $('#passwordError').text('영문, 숫자, 특수문자를 조합한 8자 이상이어야 합니다.'); // 오류 메시지 출력
        } else {
            $('#passwordError').text(''); // 오류 메시지 제거
        }
        checkFormValidity();
    });

    $('input[name="confirmPassword"]').on('input', function() {
        var password = $('input[name="password"]').val();
        if (!validateConfirmPassword(password, $(this).val())) {
            $('#confirmPasswordError').text('비밀번호가 일치하지 않습니다.'); // 오류 메시지 출력
        } else {
            $('#confirmPasswordError').text(''); // 오류 메시지 제거
        }
        checkFormValidity();
    });

    $('#signupForm').on('submit', function(event) {
        event.preventDefault(); // 폼 제출을 막음
        checkFormValidity(); // 폼 제출 전 최종 유효성 검사
        if (!$('#div_next').prop('disabled')) {
            alert('Form is valid! Submitting...');
            // 폼을 제출하거나 AJAX 요청을 보낼 수 있습니다.
            // $(this).unbind('submit').submit();
        }
    });
});