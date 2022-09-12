
export class UserBook{

    constructor(
        public bookId: string | null,
        public startedDate: Date | null,
        public completedDate:Date | null,
        public readingStatus: Date | null,
        public rating: Number | null
    ){

    }
}