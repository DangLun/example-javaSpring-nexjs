"use client";
import {
  Breadcrumb,
  BreadcrumbItem,
  BreadcrumbLink,
  BreadcrumbList,
  BreadcrumbPage,
  BreadcrumbSeparator,
  Textarea,
  Form,
  FormControl,
  FormField,
  FormItem,
  FormLabel,
  FormMessage,
  Select,
  SelectContent,
  SelectItem,
  SelectTrigger,
  SelectValue,
  Input,
  Card,
  CardContent,
  CardHeader,
  CardTitle,
  Button,
} from "@/components/ui";
import React, { useEffect, useState } from "react";
import { z } from "zod";
import { zodResolver } from "@hookform/resolvers/zod";
import { useForm } from "react-hook-form";
import Link from "next/link";
import axios from "axios";
import { useToast } from "@/hooks/use-toast";

import { ReloadIcon } from "@radix-ui/react-icons";
import { useParams } from "next/navigation";
import { ResponseBook } from "@/app/types/types";

const bookSchema = z.object({
  bookName: z.string().min(1, "Tên sách không được để trống"),
  bookPrice: z.number().min(0, "Giá sách phải lớn hơn hoặc bằng 0"),
  bookQuantity: z.number().min(0, "Số lượng phải lớn hơn hoặc bằng 0"),
  bookDics: z.string(),
  bookPicture: z
    .instanceof(File, { message: "Vui lòng chọn file ảnh cho sách" })
    .refine((file) => file.size <= 2 * 1024 * 1024, {
      message: "File phải nhỏ hơn 2MB",
    })
    .nullable(),
  bookDiscount: z.number().min(0, "Giảm giá phải lớn hơn hoặc bằng 0"),
  topic_id: z.number(),
  author_id: z.number(),
});

const UpdateBook = () => {
  const [loading, setLoading] = useState<boolean>(false);
  const params = useParams();
  const { toast } = useToast();
  const apiUrl = process.env.NEXT_PUBLIC_API_URL;
  const { id } = params;
  const form = useForm<z.infer<typeof bookSchema>>({
    resolver: zodResolver(bookSchema),
    defaultValues: {
      bookName: "",
      bookPrice: 0,
      bookQuantity: 0,
      bookDics: "",
      bookPicture: undefined,
      bookDiscount: 0,
      topic_id: 1,
      author_id: 1,
    },
  });

  const setValueForm = (book: ResponseBook) => {
    form.setValue("bookName", book.bookName);
    form.setValue("bookPrice", book.bookPrice);
    form.setValue("bookQuantity", book.bookQuantity);
    form.setValue("bookDics", book.bookDics);
    form.setValue("bookPicture", book.bookPicture);
    form.setValue("bookDiscount", book.bookDiscount);
    form.setValue("topic_id", book.topic.topicId);
    form.setValue("author_id", book.author.authorId);
  };

  // get-book-by-id

  useEffect(() => {
    axios.get(`${apiUrl}/book/getBookById/${id}`).then((res) => {
      setValueForm(res.data);
    });
  }, [id]);

  function onSubmit(values: z.infer<typeof bookSchema>) {
    const formData = new FormData();
    formData.append("bookId", id.toString());
    formData.append("bookName", values.bookName);
    formData.append("bookPrice", values.bookPrice.toString());
    formData.append(
      "bookPicture",
      values.bookPicture === null ? "" : values.bookPicture
    );
    formData.append("bookDics", values.bookDics);
    formData.append("bookQuantity", values.bookQuantity.toString());
    formData.append("bookDiscount", values.bookDiscount.toString());
    formData.append("authorId", values.author_id.toString());
    formData.append("topicId", values.topic_id.toString());

    (async () => {
      setLoading(true);
      await axios
        .put(`${apiUrl}/book/updateBook`, formData, {
          withCredentials: true,
        })
        .then((res) => {
          if (res) {
            toast({
              title: "Thành công",
              description: "Thay đổi thông tin sách thành công",
              className: "bg-green-500 border-green-500 text-white",
            });
          }
        })
        .catch(() => {
          toast({
            title: "Thất bại",
            description: "Thay đổi thông tin sách thất bại",
            className: "bg-red-500 border-red-500 text-white",
          });
        })
        .finally(() => {
          setLoading(false);
        });
    })();
  }

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
                <BreadcrumbLink asChild>
                  <Link href="/book">Sách</Link>
                </BreadcrumbLink>
              </BreadcrumbItem>
              <BreadcrumbSeparator />
              <BreadcrumbItem>
                <BreadcrumbPage>Thay đổi thông tin sách</BreadcrumbPage>
              </BreadcrumbItem>
            </BreadcrumbList>
          </Breadcrumb>
          <CardTitle className="text-2xl">Thay đổi thông tin sách</CardTitle>
        </CardHeader>
        <CardContent>
          <Form {...form}>
            <form onSubmit={form.handleSubmit(onSubmit)} className="space-y-8">
              <div className="flex items-start gap-5 w-full">
                <div className="w-[50%] space-y-8">
                  <FormField
                    control={form.control}
                    name="bookName"
                    render={({ field }) => (
                      <FormItem>
                        <FormLabel>Tên Sách</FormLabel>
                        <FormControl>
                          <Input placeholder="Nhập tên sách" {...field} />
                        </FormControl>
                        <FormMessage />
                      </FormItem>
                    )}
                  />

                  <FormField
                    control={form.control}
                    name="bookPrice"
                    render={({ field }) => (
                      <FormItem>
                        <FormLabel>Giá Sách</FormLabel>
                        <FormControl>
                          <Input
                            placeholder="Nhập giá sách"
                            type="number"
                            {...field}
                            onChange={(e) =>
                              field.onChange(parseInt(e.target.value, 10) || 0)
                            }
                          />
                        </FormControl>
                        <FormMessage />
                      </FormItem>
                    )}
                  />

                  <FormField
                    control={form.control}
                    name="bookQuantity"
                    render={({ field }) => (
                      <FormItem>
                        <FormLabel>Số Lượng</FormLabel>
                        <FormControl>
                          <Input
                            placeholder="Nhập số lượng"
                            type="number"
                            {...field}
                            onChange={(e) =>
                              field.onChange(parseInt(e.target.value, 10) || 0)
                            }
                          />
                        </FormControl>
                        <FormMessage />
                      </FormItem>
                    )}
                  />

                  <FormField
                    control={form.control}
                    name="bookDics"
                    render={({ field }) => (
                      <FormItem>
                        <FormLabel>Mô Tả</FormLabel>
                        <FormControl>
                          <Textarea placeholder="Nhập mô tả sách" {...field} />
                        </FormControl>
                        <FormMessage />
                      </FormItem>
                    )}
                  />
                </div>
                <div className="w-[50%] space-y-8">
                  <FormField
                    control={form.control}
                    name="bookPicture"
                    render={({ field }) => (
                      <FormItem>
                        <FormLabel>Ảnh của sách</FormLabel>
                        <FormControl>
                          <Input
                            type="file"
                            accept="image/*"
                            onChange={(e) => {
                              field.onChange(e.target.files?.[0]);
                            }}
                          />
                        </FormControl>
                        <FormMessage />
                      </FormItem>
                    )}
                  />

                  <FormField
                    control={form.control}
                    name="bookDiscount"
                    render={({ field }) => (
                      <FormItem>
                        <FormLabel>Giảm Giá</FormLabel>
                        <FormControl>
                          <Input
                            placeholder="Nhập giảm giá"
                            type="number"
                            {...field}
                            onChange={(e) =>
                              field.onChange(parseInt(e.target.value, 10) || 0)
                            }
                          />
                        </FormControl>
                        <FormMessage />
                      </FormItem>
                    )}
                  />

                  <FormField
                    control={form.control}
                    name="topic_id"
                    render={({ field }) => (
                      <FormItem>
                        <FormLabel>Chọn chủ đề</FormLabel>
                        <FormControl>
                          <Select
                            onValueChange={(value) =>
                              field.onChange(parseInt(value, 10))
                            }
                            defaultValue={field.value.toString()}
                          >
                            <SelectTrigger className="w-full">
                              <SelectValue placeholder="Chọn chủ đề" />
                            </SelectTrigger>
                            <SelectContent>
                              <SelectItem value="1">Trinh Thám</SelectItem>
                              <SelectItem value="2">Kinh dị</SelectItem>
                              <SelectItem value="3">Vui Nhộn</SelectItem>
                            </SelectContent>
                          </Select>
                        </FormControl>
                        <FormMessage />
                      </FormItem>
                    )}
                  />
                  <FormField
                    control={form.control}
                    name="author_id"
                    render={({ field }) => (
                      <FormItem>
                        <FormLabel>Chọn tác giả</FormLabel>
                        <FormControl>
                          <Select
                            onValueChange={(value) =>
                              field.onChange(parseInt(value, 10))
                            }
                            defaultValue={field.value.toString()}
                          >
                            <SelectTrigger className="w-full">
                              <SelectValue placeholder="Chọn chủ đề" />
                            </SelectTrigger>
                            <SelectContent>
                              <SelectItem value="1">
                                Trần Duy Đăng - Đồng Tháp
                              </SelectItem>
                              <SelectItem value="2">
                                Bùi Văn A - An Giang
                              </SelectItem>
                              <SelectItem value="3">
                                Phạm Hữu Tài - Tp.HCM
                              </SelectItem>
                            </SelectContent>
                          </Select>
                        </FormControl>
                        <FormMessage />
                      </FormItem>
                    )}
                  />
                </div>
              </div>
              {loading !== true ? (
                <Button type="submit">Thay đổi thông tin</Button>
              ) : (
                <Button disabled>
                  <ReloadIcon className="mr-2 h-4 w-4 animate-spin" />
                  Vui lòng đợi
                </Button>
              )}
            </form>
          </Form>
        </CardContent>
      </Card>
    </>
  );
};

export default UpdateBook;
