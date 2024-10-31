/**
 * Login Interface
 * 
 * @author Javier Huang
 */

export interface ILoginBaseInfo {
    sid: number,
    studentId: number,
    firstName: string,
    middleName: string,
    lastName: string,
    email: string,
    grade: number,
    homeroom: string,
    groupName: string,
    groupDescription: string,
    accessLevel: string
}

export interface ILoginSearch {
    email: string
}

export interface IloginSignup {
    uid: string,
    studentId: number,
    firstName: string,
    middleName?: string,
    lastName: string,
    email: string,
    grade: number,
    homeroom: string,
    accessLevel: string
}