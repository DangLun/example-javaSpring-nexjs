"use client";
import {
  Table,
  TableBody,
  TableCell,
  TableHead,
  TableHeader,
  TableRow,
} from "@/components/ui";

import {
  Breadcrumb,
  BreadcrumbItem,
  BreadcrumbLink,
  BreadcrumbList,
  BreadcrumbPage,
  BreadcrumbSeparator,
} from "@/components/ui/breadcrumb";

import {
  DropdownMenu,
  DropdownMenuContent,
  DropdownMenuItem,
  DropdownMenuLabel,
  DropdownMenuTrigger,
} from "@/components/ui/dropdown-menu";
import { Card, CardContent, CardHeader } from "@/components/ui/card";

import { Button } from "@/components/ui/button";
import { MoreHorizontal, Plus } from "lucide-react";

import React, { useEffect, useState } from "react";
import Image from "next/image";
import { Book } from "@/app/types/types";
import Link from "next/link";
import { Input } from "@/components/ui";
import axios from "axios";
import { useRouter } from "next/navigation";
import { CustomAlertDialog } from "@/app/components/CustomAlertDialog";
import { toast } from "@/hooks/use-toast";
import { useDebounce } from "@/hooks/use-debounce";

const Books = () => {
  const [bookData, setBookData] = useState<Book[]>([]);
  const [currentId, setCurrentId] = useState<number>(0);
  const [isOpen, setIsOpen] = useState<boolean>(false);
  const [searchText, setSearchText] = useState<string>("");
  const router = useRouter();
  // // reactjs
  // const navigate = useNavigate()
  // navigate("/")

  const debounce = useDebounce(searchText, 500); // 

  const apiUrl = process.env.NEXT_PUBLIC_API_URL;
  
  useEffect(() => {
    (async () => {
      const response = await axios.get(`${apiUrl}/book/getAll`, {
        withCredentials: true,
      });
      const books = response.data;
      setBookData(books);
    })();
  }, []);

  useEffect(() => {
    axios
      .get(`${apiUrl}/book/search-by-name`, {
        params: {
          bookName: searchText,
        },
      })
      .then((res) => {
        setBookData(res.data);
      });
  }, [debounce]);

  const deleteBookAsync = async (id: number) => {
    await axios
      .delete(`${apiUrl}/book/deleteBook`, {
        params: {
          bookId: id,
        },
      })
      .then(() => {
        setBookData(bookData.filter((book) => book.bookId !== id));
        setIsOpen(false);
        toast({
          title: "Thành công",
          description: "Xóa thành công sách có mã: " + id,
          className: "bg-green-500 border-green-500 text-white",
        });
      });
  };

  return (
    <>
      <Card>
        <CardHeader>
          <Breadcrumb>
            <BreadcrumbList>
              <BreadcrumbItem>
                <BreadcrumbLink asChild>
                  <Link href="/">Trang chủ</Link>
                </BreadcrumbLink>
              </BreadcrumbItem>
              <BreadcrumbSeparator />
              <BreadcrumbItem>
                <BreadcrumbPage>Sách</BreadcrumbPage>
              </BreadcrumbItem>
            </BreadcrumbList>
          </Breadcrumb>
        </CardHeader>
        <CardContent>
          <div className="flex items-center justify-between">
            <Link href={"/book/addbook"}>
              <Button>
                <Plus className="mr-2 h-4 w-4" /> Thêm sách mới
              </Button>
            </Link>
            <Input
              placeholder="Tìm kiếm sách...."
              value={searchText}
              className="w-[350px]"
              onChange={(e) => {
                setSearchText(e.target.value);
              }}
            />
          </div>

          <Table className="mt-3 min-w-full rounded-lg">
            <TableHeader>
              <TableRow>
                <TableHead className="w-[100px]">#</TableHead>
                <TableHead>Hình ảnh</TableHead>
                <TableHead>Tên sách</TableHead>
                <TableHead>Giá tiền</TableHead>
                <TableHead>Giảm giá</TableHead>
                <TableHead>Giá tiền được bán</TableHead>
                <TableHead>Số lượng tồn</TableHead>
                <TableHead>Mô tả</TableHead>
                <TableHead>Thao tác</TableHead>
              </TableRow>
            </TableHeader>
            <TableBody>
              {bookData.length > 0 ? (
                bookData.map((book, index) => {
                  return (
                    <TableRow key={index}>
                      <TableCell className="font-medium">
                        {book.bookId}
                      </TableCell>
                      <TableCell>
                        <Image
                          src={apiUrl + book.bookPicture}
                          alt="hinh"
                          width={100}
                          height={100}
                        />
                      </TableCell>
                      <TableCell>{book.bookName}</TableCell>
                      <TableCell>${book.bookPrice}</TableCell>
                      <TableCell>{book.bookDiscount * 10}%</TableCell>
                      <TableCell>
                        ${book.bookPrice - book.bookDiscount * book.bookPrice}
                      </TableCell>
                      <TableCell>{book.bookQuantity}</TableCell>
                      <TableCell>{book.bookDics}</TableCell>
                      <TableCell>
                        <DropdownMenu>
                          <DropdownMenuTrigger asChild>
                            <Button variant="ghost" className="h-8 w-8 p-0">
                              <MoreHorizontal className="h-4 w-4" />
                            </Button>
                          </DropdownMenuTrigger>
                          <DropdownMenuContent align="end">
                            <DropdownMenuLabel>Thao tác</DropdownMenuLabel>
                            <DropdownMenuItem
                              onClick={() =>
                                router.push(`/book/updatebook/${book.bookId}`)
                              }
                            >
                              Sửa Sách
                            </DropdownMenuItem>
                            <DropdownMenuItem
                              onClick={() => {
                                const id = book.bookId;
                                setCurrentId(id);
                                setIsOpen(true);
                              }}
                            >
                              Xóa sách
                            </DropdownMenuItem>
                          </DropdownMenuContent>
                        </DropdownMenu>
                      </TableCell>
                    </TableRow>
                  );
                })
              ) : (
                <TableRow>
                  <TableCell colSpan={8} className="h-24 text-center">
                    Không có dữ liệu
                  </TableCell>
                </TableRow>
              )}
            </TableBody>
          </Table>
        </CardContent>
        <CustomAlertDialog
          title="Thông báo xóa"
          id={currentId}
          onCancel={() => {
            setIsOpen(false);
          }}
          isOpen={isOpen}
          onAgree={deleteBookAsync}
          Description="Bạn có chắc chắn muốn xóa sách này không ? "
        />
        ;
      </Card>
    </>
  );
};

export default Books;




// book -> updatebook -> [id] -> page.tsx