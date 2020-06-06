import LocalizedStrings from 'react-localization';
import {
    ILLNESS_DESCRIPTION_MAX_LENGTH,
    ILLNESS_DESCRIPTION_MIN_LENGTH,
    DOCTOR_FIRST_NAME_MAX_LENGTH,
    DOCTOR_FIRST_NAME_MIN_LENGTH,
    DOCTOR_LAST_NAME_MIN_LENGTH,
    DOCTOR_LAST_NAME_MAX_LENGTH,
    DOCTOR_MIDDLE_NAME_MAX_LENGTH,
    DOCTOR_MIDDLE_NAME_MIN_LENGTH,
    DOCTOR_PHONE_LENGTH,
    ILLNESS_NAME_MAX_LENGTH,
    ILLNESS_NAME_MIN_LENGTH,
    USERNAME_MIN_LENGTH,
    PASSWORD_MIN_LENGTH,
    MAX_PRICE,
    USERNAME_MAX_LENGTH,
    PASSWORD_MAX_LENGTH,
    ILLNESS_CHANCE_TO_DIE_MAX,
    ILLNESS_CHANCE_TO_DIE_MIN
} from "../constants";

export const localizedStrings = new LocalizedStrings({

        en: {
            helpSearchLastName: 'Search by last name',
            helpSearchIllnessName: 'Search by illnesses names',
            asc: "Ascending",
            desc: "Descending",
            logout: "Logout",
            login: "Login",
            signUp: "Sign up",
            doctors: "Doctors",
            shopName: "Hospital",
            logInWithGoogle: "Log in with Google",
            logInWithGithub: "Log in with github",
            loginFormRegisterNow: " register now!",
            alreadyRegister: "Already registered?",
            signUpFromLoginNow: "Login now!",
            or: "or",
            password: "Password",
            loginField: "Login",
            editDoctor: 'Update doctor',
            orderCreated: 'Order created',

            page:"page",
            sort: 'Sort: ',
            sortByLastName: 'By last name',
            sortByDateOfBirth: 'By date of birth',


            firstName: "First name",
            lastName: "Last name",
            middleName: "Middle name",
            dateOfBirth: "Date of birth",
            phone: "Phone",
            name: "Name",
            chanceToDie: "Chance to die",
            description: "Description",
            price: "Price per consultation",
            days: "days",
            illness: "Illness",
            //
            profile: "Profile",
            addDoctor: "Add doctor",


            ///params
            username: "Username",
            password: "Password",
            confPassword: "Confirmed password",
            //alerts
            alertBadUsername: "Please input your username!",
            alertBadPassword: "Please input your Password!",
            alertSuccessRegister: "Thank you! You're successfully registered. Please Login to continue!",

            alertException: "Sorry! Temporary problems. Try later!",


            alertBadIllnessNameTooShort: `Illness name is too short (Minimum ${ILLNESS_NAME_MIN_LENGTH} characters)`,
            alertBadIllnessNameTooLong: `Illness name is too long (Maximum ${ILLNESS_NAME_MAX_LENGTH} characters)`,

            alertBadIllnessDescriptionTooLong: `Description is too long (Maximum ${ILLNESS_DESCRIPTION_MAX_LENGTH} characters)`,
            alertBadIllnessDescriptionTooShort:`Description is too short (Minimum ${ILLNESS_DESCRIPTION_MIN_LENGTH} characters)`,

            alertBadIllnessChanceTooSmall: `Chance is too small (${ILLNESS_CHANCE_TO_DIE_MIN})`,
            alertBadIllnessChanceTooBig: `Chance is too big (${ILLNESS_CHANCE_TO_DIE_MAX})`,
            alertBadIllnessChance: `Incorrect chance to die`,

            alertBadDoctorPriceTooSmall: 'Price can`t be <0',
            alertBadDoctorPriceTooBig: `Price is too big (Maximum ${MAX_PRICE}  allowed )`,
            alertBadDoctorPrice: `Incorrect price`,

            alertBadDoctorFirstNameTooShort: `First name is too short (Minimum ${DOCTOR_FIRST_NAME_MIN_LENGTH} characters;\nMaximum ${DOCTOR_FIRST_NAME_MAX_LENGTH} characters )`,
            alertBadDoctorFirstNameTooLong: `First name is too long (Minimum ${DOCTOR_FIRST_NAME_MIN_LENGTH} characters;\nMaximum ${DOCTOR_FIRST_NAME_MAX_LENGTH} characters )`,
            
            alertBadDoctorLastNameTooShort: `Last name is too short (Minimum ${DOCTOR_LAST_NAME_MIN_LENGTH} characters;\nMaximum ${DOCTOR_LAST_NAME_MAX_LENGTH} characters )`,
            alertBadDoctorLastNameTooLong: `Last name is too long (Minimum ${DOCTOR_LAST_NAME_MIN_LENGTH} characters;\nMaximum ${DOCTOR_LAST_NAME_MAX_LENGTH} characters )`,

            alertBadDoctorMiddleNameTooShort: `Middle name is too short (Minimum ${DOCTOR_MIDDLE_NAME_MIN_LENGTH} characters;\nMaximum ${DOCTOR_MIDDLE_NAME_MAX_LENGTH} characters )`,
            alertBadDoctorMiddleNameTooLong: `Middle name is too long (Minimum ${DOCTOR_MIDDLE_NAME_MIN_LENGTH} characters;\nMaximum ${DOCTOR_MIDDLE_NAME_MAX_LENGTH} characters )`,

            alertBadUsernameTooShort: `Username is too short (Minimum ${USERNAME_MIN_LENGTH} characters;\nMaximum ${USERNAME_MAX_LENGTH} characters )`,
            alertBadUsernameTooLong: `Username is too long (Maximum ${USERNAME_MAX_LENGTH} characters;\nMinimum ${USERNAME_MIN_LENGTH} characters )`,

            alertBadDoctorDateOfBirth: `Date can be past or present`,

            alertBadPasswordTooShort: `Password is too short (Minimum ${PASSWORD_MIN_LENGTH} characters;\nMaximum ${PASSWORD_MAX_LENGTH} characters )`,
            alertBadPasswordTooLong: `Password is too long (Maximum ${PASSWORD_MAX_LENGTH} characters;\nMinimum ${PASSWORD_MIN_LENGTH} characters )`,
            alertNotEqualPasswords: `Confirmed password not equals password`,

            alertBadDoctorPhone: `Phone number can be ${DOCTOR_PHONE_LENGTH} digits`,
            alertDeleteDoctorSuccessfully: 'Doctor deleted successfully!',
            alertEditDoctorSuccessfully: 'Doctor edited successfully!',
            alertAddDoctorSuccessfully: 'Doctor added successfully!',

            alertAppName: 'Hospital',
            alertLoggedOut: 'You have been logged out. Please login to do it.',


            alertWrongUsernameOrPassword: 'Your Username or Password is incorrect. Please try again!',
            alertSuccessLogin: 'Welcome!',

            alertRebootPage: 'Reboot? The changes you made may not be saved.',

            alertNoPermission: 'No permissions, Sorry!',
            alertSuccessLogOut: 'Good bye!',
            ///helpers
            helpForUsername:`A username between ${USERNAME_MIN_LENGTH} to ${USERNAME_MAX_LENGTH} characters`,
            helpForPass: `A password between ${PASSWORD_MIN_LENGTH} to ${PASSWORD_MAX_LENGTH} characters`,
            helpForIllnessName: "Enter illness name",
            helpForIllnessDescription: "Enter illness description",
            helpForFirstName: "Enter first name",
            helpForLastName: "Enter last name",
            helpForMiddleName: "Enter middle name",
            helpForPhone: "Enter phone number",
            helpForDoctorPrice: "Enter price per consultation",
            helpSearch: "Search...",

            helpDeleteModal: "Do you want to delete doctor?",
            helpCancel: "Cancel",
            helpOk: "Ok",

            helpOrderDoctor: "Are you sure?",

            alreadyOrder: "Ordered",
            //footer
            footerText: "2020 All Rights Reserved",

            //buttons
            edit: "Edit",
            delete: "Delete",
            add: "Save",
            order: "Order",
            addIllness: "Add a illness",
            search: "Search",
            selectShowAllDoctors: "All",
            selectShowMyOrders: "My orders",

            notFound:"The Page you're looking for was not found.",
            back: "Go back",

            //months
            january: "January",
            february: "February",
            march: "March",
            april: "April",
            may: "May",
            june: "June",
            july: "July",
            august: "August",
            september: "September",
            october: "October",
            november: "November",
            december: "December",
            ///
        },
        ru: {
            helpSearchLastName: 'Поиск по фамилии',
            helpSearchIllnessName: 'Поиск по названия болезней',
            asc: "По возрастанию",
            desc: "По убыванию",
            logout: "Выйти",
            login: "Войти",
            signUp: "Зарегистрироваться",
            doctors: "Врачи",
            shopName: "Больница",
            logInWithGoogle: "Войти через Google",
            logInWithGithub: "Войти через github",
            alreadyRegister: "Уже зарегистрированы?",
            loginFormRegisterNow: " зарегистрируйся сейчас!",
            signUpFromLoginNow: "Залогиньтесь!",
            or: "или",
            password: "Пароль",
            loginField: "Логин",
            editDoctor: 'Изменить врача',
            orderCreated: "заказ сделан",

            sort: 'Сортировать: ',
            sortByLastName: 'По фамилии',
            sortByDateOfBirth: 'По дате рождения',
            page: "страница",

            firstName: "Имя",
            lastName: "Фамилия",
            middleName: "Отчество",
            dateOfBirth: "Дата рождения",
            phone: "Телефон",
            name: "Название",
            chanceToDie: "Шанс смерти",
            description: "Описание",
            price: "Цена за консультацию",
            days: "дней",
            illness: "Болезнь",
            //

            profile: "Профиль",
            addDoctor: "Добавить врача",

            ///params
            username: "Никнейм",
            password: "Пароль",
            confPassword: "Подтвержденный пароль",
            //alerts
            alertBadUsername: "Пожалуйста, введите Ваш никнейм",
            alertBadPassword: "Пожалуйста, введите Ваш пароль",
            alertSuccessRegister: "Спасибо! Вы успешно зарегистрированы. Пожалуйста, залогиньтесь для продолжения!",

            alertException: "Извините! Временные неполадки. Попробуйте позже!",

            alertRebootPage: 'Перезагрузить? Возможно, внесенные изменения не сохранятся.',

            alertBadIllnessNameTooShort: `Название болезни очень короткий! (Минимум ${ILLNESS_NAME_MIN_LENGTH} символов)`,
            alertBadIllnessNameTooLong: `Название болезни очень длинный (Максимум ${ILLNESS_NAME_MAX_LENGTH} символов)`,

            alertBadIllnessDescriptionTooLong: `Описание очень длинное (Максимум ${ILLNESS_DESCRIPTION_MAX_LENGTH} символов)`,
            alertBadIllnessDescriptionTooShort: `Описание очень длинное (Максимум ${ILLNESS_DESCRIPTION_MIN_LENGTH} символов)`,

            alertBadIllnessChanceTooSmall:`Шанс смерти маленький(Минимум ${ILLNESS_CHANCE_TO_DIE_MIN})`,
            alertBadIllnessChanceTooBig:`Шанс смерти большой(Максимум ${ILLNESS_CHANCE_TO_DIE_MAX})`,
            alertBadIllnessChance: `Некорректный шанс смерти`,
            
            alertBadDoctorPriceTooBig: `Стоимость слишком большая (Максимум ${MAX_PRICE}  )`,
            alertBadDoctorPriceTooSmall: 'Стоимость не может быть меньше 0',
            alertBadDoctorPrice: `Некорректная цена`,

            alertBadDoctorFirstNameTooShort: `Имя очень короткое (Минимум ${DOCTOR_FIRST_NAME_MIN_LENGTH} символов;\nМаксимум ${DOCTOR_FIRST_NAME_MAX_LENGTH} символов )`,
            alertBadDoctorFirstNameTooLong: `Имя очень длинное (Максимум ${DOCTOR_FIRST_NAME_MAX_LENGTH} символов;\nМинимум ${DOCTOR_FIRST_NAME_MIN_LENGTH} символов )`,
            
            alertBadDoctorLastNameTooShort: `Фамилия очень короткое (Минимум ${DOCTOR_LAST_NAME_MIN_LENGTH} символов;\nМаксимум ${DOCTOR_LAST_NAME_MAX_LENGTH} символов )`,
            alertBadDoctorLastNameTooLong: `Фамилия очень длинное (Максимум ${DOCTOR_LAST_NAME_MAX_LENGTH} символов;\nМинимум ${DOCTOR_LAST_NAME_MIN_LENGTH} символов )`,

            alertBadDoctorMiddleNameTooShort: `Отчество очень короткое (Минимум ${DOCTOR_MIDDLE_NAME_MIN_LENGTH} символов;\nМаксимум ${DOCTOR_MIDDLE_NAME_MAX_LENGTH} символов )`,
            alertBadDoctorMiddleNameTooLong: `Отчество очень длинное (Максимум ${DOCTOR_MIDDLE_NAME_MAX_LENGTH} символов;\nМинимум ${DOCTOR_MIDDLE_NAME_MIN_LENGTH} символов )`,

            alertBadUsernameTooShort: `Никнейм очень короткий (Минимум ${USERNAME_MIN_LENGTH} символов;\nМаксимум ${USERNAME_MAX_LENGTH} символов )`,
            alertBadUsernameTooLong: `Никнейм очень длинный (Максимум ${USERNAME_MAX_LENGTH} символов;\nМинимум ${USERNAME_MIN_LENGTH} символов )`,

            alertBadDoctorDateOfBirth: `Дата можеть быть прошлой или текущей`,

            alertBadPasswordTooShort: `Пароль очень короткий (Минимум ${PASSWORD_MIN_LENGTH} символов;\nМаксимум ${PASSWORD_MAX_LENGTH} символов )`,
            alertBadPasswordTooLong: `Пароль очень длинный (Максимум ${PASSWORD_MAX_LENGTH} символов;\nМинимум ${PASSWORD_MIN_LENGTH} символов )`,
            alertNotEqualPasswords:`Пароли не совпадают.`,

            alertBadDoctorPhone: `Номер телефона может иметь ${DOCTOR_PHONE_LENGTH} цифр`,
            alertDeleteDoctorSuccessfully: 'Врач удален успешно!',
            alertEditDoctorSuccessfully: 'Врач изменен успешно!',
            alertAddDoctorSuccessfully: 'Врач добавлен успешно!',

            alertAppName: 'Госпиталь',
            alertLoggedOut: 'Вы вышли из системы. Пожалуйста, залогиньтесь для этого действия.',

            alertWrongUsernameOrPassword: 'Ваш логин или пароль неверны. Пожалуйста, попробуйте еще раз!',

            alertSuccessLogin: 'Добро пожаловать!',

            alertNoPermission: 'У вас нет прав, хитрюга!',
            alertSuccessLogOut: 'Успешный выход!',
            ///helpers
            helpForPass: `Пароль должен быть от ${PASSWORD_MIN_LENGTH} до ${PASSWORD_MAX_LENGTH} символов`,
            helpForUsername: `Никнейм должен быть от ${USERNAME_MIN_LENGTH} до ${USERNAME_MAX_LENGTH} символов`,
            helpForIllnessName: "Введите название болезни",
            helpForIllnessDescription: "Введите описание болезни",
            helpForFirstName:"Введите имя",
            helpForLastName:"Введите фамилию",
            helpForMiddleName:"Введите отчество",
            helpForPhone: "Введите номер",
            helpForDoctorPrice: "Введите стоимость консультации",
            helpSearch: "Поиск...",

            helpDeleteModal: "Вы действительно хотите удалить врача?",
            helpCancel: "Закрыть",
            helpOk: "Потдвердить",

            helpOrderDoctor: "Вы уверены?",
            alreadyOrder: "Заказан",
            //footer
            footerText: "2020 Все права защищены",

            //buttons
            edit:"Изменить",
            delete:"Удалить",
            add:"Сохранить",
            order:"Заказать",
            addIllness:"Добавить болезнь",

            search: "Поиск",
            selectShowAllDoctors: "Всё",
            selectShowMyOrders: "Мои заказы",

            notFound: 'Страница, которую вы ищете, не найдена.',
            back: "Назад",

            //months
            january: "Январь",
            february: "Февраль",
            march: "Март",
            april: "Апрель",
            may: "Май",
            june: "Июнь",
            july: "Июль",
            august: "Август",
            september: "Сентябрь",
            october: "октябрь",
            november: "Ноябрь",
            december: "Декабрь",
            ///


        }
    })
;