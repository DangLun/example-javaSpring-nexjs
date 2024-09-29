import {
  AlertDialog,
  AlertDialogAction,
  AlertDialogCancel,
  AlertDialogContent,
  AlertDialogDescription,
  AlertDialogFooter,
  AlertDialogHeader,
  AlertDialogTitle,
} from "@/components/ui";

export interface IAlertDialogProps {
  isOpen: boolean;
  id: number;
  title: string;
  Description: string;
  onAgree: (id: number) => void;
  onCancel: () => void;
}

export function CustomAlertDialog(props: IAlertDialogProps) {
  return (
    <AlertDialog open={props.isOpen}>
      <AlertDialogContent>
        <AlertDialogHeader>
          <AlertDialogTitle>{props.title}</AlertDialogTitle>
          <AlertDialogDescription>{props.Description}</AlertDialogDescription>
        </AlertDialogHeader>
        <AlertDialogFooter>
          <AlertDialogCancel onClick={props.onCancel}>Hủy</AlertDialogCancel>
          <AlertDialogAction onClick={() => props.onAgree(props.id)}>
            Đồng ý
          </AlertDialogAction>
        </AlertDialogFooter>
      </AlertDialogContent>
    </AlertDialog>
  );
}
