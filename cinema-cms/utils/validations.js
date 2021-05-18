export const change_password = {
  old_password: [
    "old_password",
    {
      rules: [
        { whitespace: true, message: "Không được có ký tự trống ở đầu hoặc cuối input" },
      ]
    }
  ],
  password: [
    "password",
    {
      rules: [
        { whitespace: true, message: "Không được có ký tự trống ở đầu hoặc cuối input" },
      ]
    }
  ],
  password_confirmation: [
    "password_confirmation",
    {
      rules: [
        { whitespace: true, message: "Không được có ký tự trống ở đầu hoặc cuối input" },
      ]
    }
  ],
}