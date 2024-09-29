export type Book = {
  bookId: number;
  bookName: string;
  bookPrice: number;
  bookQuantity: number;
  bookDics: string;
  bookPicture: string;
  bookDiscount: number;
  createAt: Date;
  updateAt: Date;
};
export interface Book1 {
  bookId: number;
  bookName: string;
  bookPrice: number;
  bookQuantity: number;
  bookDics: string;
  bookPicture: string;
  bookDiscount: number;
  createAt: Date;
  updateAt: Date;
};


export type ResponseBook = {
  bookId: number;
  bookName: string;
  bookPrice: number;
  bookQuantity: number;
  bookDics: string;
  bookPicture: File;
  bookDiscount: number;
  topic: {
    topicId: number;
  };
  author: {
    authorId: number;
  };
};

export type Author = {
  authorId: number;
  authorName: string;
  authorAddress: string;
  authorBio: string;
  authorPhone: string;
};

export type Topic = {
  topicId: number;
  topicName: string;
};
